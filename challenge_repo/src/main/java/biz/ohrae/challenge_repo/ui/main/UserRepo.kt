package biz.ohrae.challenge_repo.ui.main

import androidx.compose.runtime.snapshots.SnapshotStateList
import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.profile.NicknameState
import biz.ohrae.challenge_repo.model.register.ImageBucket
import biz.ohrae.challenge_repo.model.user.*
import biz.ohrae.challenge_repo.util.PagerMeta
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun login(): Flow<FlowResult> {
        val serviceIds = prefs.getZeServiceIds()
        val customer = prefs.getZeCustomer()
        if (serviceIds.isNullOrEmpty()) {
            return flow {
                emit(FlowResult(false, "", ""))
            }
        }

        val jsonArray = JsonArray()
        serviceIds.forEach {
            jsonArray.add(it)
        }

        val jsonObject = JsonObject()
        jsonObject.addProperty("serviceType", "ze_study")
        jsonObject.add("service_ids", jsonArray)

        val response = apiService.login(customer?.access_token.toString(), jsonObject)

        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                if (isSuccess) {
                    val user = gson.fromJson(response.body.dataset, User::class.java)
                    Timber.e("user : ${gson.toJson(user)}")
                    Timber.d("access-token : ${user.access_token}")
                    prefs.setUserData(user)
                    return flow {
                        emit(FlowResult(true, "", ""))
                    }
                } else {
                    Timber.e("error code : ${response.body.code}")
                    return flow {
                        emit(FlowResult(false, response.body.code, response.body.message))
                    }
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }

    suspend fun tokenCheck(): Flow<FlowResult> {
        val user = prefs.getUserData()
        if (user == null) {
            return flow {
                emit(FlowResult(false, "", ""))
            }
        }

        val response = apiService.authTokenCheck(user.access_token)
        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                if (isSuccess) {
                    return flow {
                        emit(FlowResult(true, "", ""))
                    }
                } else {
                    return flow {
                        emit(FlowResult(false, response.body.code, response.body.message))
                    }
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", response.errorMessage))
                }
            }
        }
    }

    suspend fun refreshToken(): Flow<FlowResult> {
        val userData = prefs.getUserData()
        if (userData == null) {
            return flow {
                emit(FlowResult(false, "", ""))
            }
        }

        val response = apiService.authTokenRefresh(userData.refresh_token)
        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                val dataSet = response.body.dataset
                if (isSuccess) {
                    if (dataSet != null) {
                        val user = gson.fromJson(dataSet, User::class.java)
                        return flow {
                            emit(FlowResult(user, "", ""))
                        }
                    } else {
                        return flow {
                            emit(FlowResult(null, "", ""))
                        }
                    }
                } else {
                    return flow {
                        emit(FlowResult(null, response.body.code, response.body.message))
                    }
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }

    suspend fun createServiceUser(): Flow<FlowResult> {
        val serviceIds = prefs.getZeServiceIds()
        if (serviceIds.isNullOrEmpty()) {
            return flow {
                emit(FlowResult(null, "", ""))
            }
        }
        val customer = prefs.getZeCustomer()
        if (customer == null) {
            return flow {
                emit(FlowResult(null, "", ""))
            }
        }

        val jsonArray = JsonArray()
        serviceIds.forEach {
            jsonArray.add(it)
        }

        val jsonObject = JsonObject()
//        jsonObject.addProperty("name", customer.name.toString())
//        jsonObject.addProperty("nickname", customer.nick_name.toString())
//        jsonObject.addProperty("phone_number", customer.phone_number)
        jsonObject.addProperty("service_type", "ze_study")
        jsonObject.add("service_ids", jsonArray)

        val accessToken = customer.access_token
        val response = apiService.createUserService(accessToken, jsonObject)

        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                Timber.e("data : ${response.body.dataset}")
                return flow {
                    emit(FlowResult(isSuccess, "", ""))
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }

    suspend fun getAllBlock(): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response = apiService.getAllBlock(accessToken.toString())

        when (response) {
            is NetworkResponse.Success -> {
                val redCardData =
                    gson.fromJson(response.body.dataset, RedCardState::class.java)
                return flow {
                    emit(FlowResult(redCardData, "", ""))
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }

    }

    suspend fun getRedCardList(): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response = apiService.getRedCardList(accessToken.toString(), 1, 10)

        when (response) {
            is NetworkResponse.Success -> {
                val dataSet = response.body.dataset?.asJsonObject
                val array = dataSet?.get("array")?.asJsonArray
                val pager =
                    gson.fromJson(dataSet?.get("meta").toString(), PagerMeta::class.java)

                val listType = object : TypeToken<SnapshotStateList<RedCardState?>?>() {}.type
                val redCardList = gson.fromJson<SnapshotStateList<RedCardState>>(array, listType)

                return flow {
                    emit(FlowResult(redCardList, "", "",pager))
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }

    }

    suspend fun getUserData(): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response = apiService.getUserData(accessToken.toString())
        when (response) {
            is NetworkResponse.Success -> {
                val userData =
                    gson.fromJson(response.body.dataset, User::class.java)
                return flow {
                    emit(FlowResult(userData, "", ""))
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }

    suspend fun getPaymentHistory(
        page: Int,
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response = apiService.getPaymentHistory(accessToken.toString(), page, 10)
        when (response) {
            is NetworkResponse.Success -> {
                val dataSet = response.body.dataset?.asJsonObject
                val array = dataSet?.get("array")?.asJsonArray
                val pager =
                    gson.fromJson(dataSet?.get("meta").toString(), PagerMeta::class.java)

                val listType = object : TypeToken<SnapshotStateList<PaymentHistoryData?>?>() {}.type
                val paymentHistoryList = gson.fromJson<SnapshotStateList<PaymentHistoryData>>(array, listType)

                return flow {
                    emit(FlowResult(paymentHistoryList, "", "",pager))
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }

    suspend fun getRewardHistory(
        type: String,
        page: Int,
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response = apiService.getRewardHistory(type, accessToken.toString(), page, 10)
        when (response) {
            is NetworkResponse.Success -> {
                val dataSet = response.body.dataset?.asJsonObject
                val array = dataSet?.get("array")?.asJsonArray
                val pager =
                    gson.fromJson(dataSet?.get("meta").toString(), PagerMeta::class.java)

                val listType = object : TypeToken<SnapshotStateList<RewardData?>?>() {}.type
                val rewardList = gson.fromJson<SnapshotStateList<RewardData>>(array, listType)
                return flow {
                    emit(FlowResult(rewardList, "", "", pager))
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", response.errorMessage))
                }
            }
        }
    }

    suspend fun uploadUserImage(imageFilePath: String): Flow<FlowResult> {
        val file = File(imageFilePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("image", file.path, requestFile)
        val accessToken = prefs.getUserData()?.access_token.toString()

        val response = apiService.uploadUserImage(accessToken, multipartBody)
        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                return if (isSuccess) {
                    flow {
                        val imageBucket =
                            gson.fromJson(response.body.dataset, ImageBucket::class.java)
                        emit(FlowResult(imageBucket, "", ""))
                    }
                } else {
                    flow {
                        val imageBucket = ImageBucket(
                            errorCode = response.body.code.toString(),
                            errorMessage = response.body.message.toString()
                        )
                        emit(FlowResult(imageBucket, response.body.code, response.body.message))
                    }
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }

    suspend fun updateUserProfile(
        nickName: String? = null,
        imageId: Int? = null
    ): Flow<FlowResult> {
        val jsonObject = JsonObject()
        if (nickName != null) {
            jsonObject.addProperty("nickname", nickName)
        }
        if (imageId != null) {
            jsonObject.addProperty("image_file_id", imageId)
        }
        val accessToken = prefs.getUserData()?.access_token.toString()

        val response = apiService.updateUserProfile(accessToken, jsonObject)
        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                return flow {
                    emit(FlowResult(isSuccess, response.body.code, response.body.message))
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }

    suspend fun checkNickname(nickName: String?): Flow<FlowResult> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("nickname", nickName)

        val accessToken = prefs.getUserData()?.access_token.toString()

        val response = apiService.checkNickname(accessToken, jsonObject)
        when (response) {
            is NetworkResponse.Success -> {
                if (response.body.success && response.body.dataset != null) {
                    val state = gson.fromJson(response.body.dataset, NicknameState::class.java)
                    return flow {
                        emit(FlowResult(state, response.body.code, response.body.message))
                    }
                } else {
                    return flow {
                        emit(FlowResult(null, response.body.code, response.body.message))
                    }
                }
            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }

    suspend fun logout(): Flow<FlowResult> {
        prefs.removeUserData()
        return flow {
            emit(FlowResult(true, null, null))
        }
    }
}