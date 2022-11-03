package biz.ohrae.challenge_repo.ui.detail

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.report.ReportDetail
import biz.ohrae.challenge_repo.model.user.PaymentHistoryState
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.model.verify.VerifyData
import biz.ohrae.challenge_repo.util.PagerMeta
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


class ChallengeDetailRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun getChallenge(id: String): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response = apiService.getChallenge(accessToken.toString(), id)

        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val challengeData =
                        gson.fromJson(response.body.dataset, ChallengeData::class.java)
                    flow {
                        emit(FlowResult(challengeData, "", ""))
                    }
                } else {
                    flow {
                        emit(FlowResult(null, "", ""))
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

    suspend fun getUserByChallenge(
        challengeId: String,
        page: Int = 1,
        count: Int = 10
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response =
            apiService.getUsersByChallenge(accessToken.toString(), challengeId, page, count)

        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val dataSet = response.body.dataset?.asJsonObject
                    val array = dataSet?.get("array")?.asJsonArray

                    val listType = object : TypeToken<List<User?>?>() {}.type
                    val challengerList = gson.fromJson<List<User>>(array, listType)

                    flow {
                        emit(FlowResult(challengerList, "", ""))
                    }
                } else {
                    flow {
                        emit(FlowResult(null, response.body.code, response.body.message))
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

    suspend fun verifyChallenge(
        challengeId: String,
        type: String,
        content: String,
        imageFilePath: String,
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val userId = prefs.getUserData()?.id

        val challengeIdData = challengeId.toRequestBody("text/plain".toMediaTypeOrNull())
        val userIdData = userId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val typeData = type.toRequestBody("text/plain".toMediaTypeOrNull())
        val commentData = content.toRequestBody("text/plain".toMediaTypeOrNull())

        val file = File(imageFilePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("image", file.path, requestFile)

        val response = apiService.verify(
            accessToken.toString(),
            multipartBody,
            challengeIdData,
            userIdData,
            typeData,
            commentData
        )
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    flow {
                        emit(FlowResult(true, "", ""))
                    }
                } else {
                    flow {
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

    suspend fun getVerifyList(
        challengeId: String, order: String, isMine: Int, page: Int
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token

        val jsonObject = JsonObject()
        val filter = JsonObject()
        filter.addProperty("order", order)
        filter.addProperty("is_mine", isMine)
        jsonObject.add("filter", filter)
        val response =
            apiService.getVerifyList(
                challengeId,
                accessToken.toString(),
                body = jsonObject,
                page,
                10
            )
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val dataSet = response.body.dataset?.asJsonObject
                    val array = dataSet?.get("array")?.asJsonArray
                    val pager =
                        gson.fromJson(dataSet?.get("meta").toString(), PagerMeta::class.java)

                    val listType = object : TypeToken<List<VerifyData?>?>() {}.type
                    val verifyList = gson.fromJson<List<VerifyData>>(array, listType)

                    flow {
                        emit(FlowResult(verifyList, "", "", pager))
                    }
                } else {
                    flow {
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

    suspend fun favoriteChallenge(
        challengeId: String, like: Int
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val jsonObject = JsonObject()
        jsonObject.addProperty("challenge_id", challengeId)
        jsonObject.addProperty("like", like)

        val response = apiService.favoriteChallenge(accessToken.toString(), jsonObject)
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {

                    flow {
                        emit(FlowResult(true, "", ""))
                    }
                } else {
                    flow {
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

    suspend fun getRegisterReport(): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token

        val response = apiService.getRegisterReport(accessToken.toString())
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val dataSet = response.body.dataset?.asJsonArray

                    val listType = object : TypeToken<List<ReportDetail?>?>() {}.type
                    val reportDetailList = gson.fromJson<List<ReportDetail>>(dataSet, listType)

                    flow {
                        emit(FlowResult(reportDetailList, "", ""))
                    }
                } else {
                    flow {
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

    suspend fun createReport(
        verificationId: String,
        userId: String,
        reportReasonCode: String
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val jsonObject = JsonObject()

        jsonObject.addProperty("verification_id", verificationId)
        jsonObject.addProperty("user_id", userId)
        jsonObject.addProperty("report_reason_code", reportReasonCode)

        val response = apiService.createReport(accessToken.toString(), jsonObject)

        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {

                    flow {
                        emit(FlowResult(true, "", ""))
                    }
                } else {
                    flow {
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

    suspend fun feedLike(verificationId: String, like: Int): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val jsonObject = JsonObject()

        jsonObject.addProperty("verification_id", verificationId)
        jsonObject.addProperty("like", like)

        val response = apiService.feedLike(accessToken.toString(), jsonObject)
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {

                    flow {
                        emit(FlowResult(true, "", ""))
                    }
                } else {
                    flow {
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


    suspend fun getChallengeResult(id: String): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response = apiService.getChallenge(accessToken.toString(), id)

        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val challengeData =
                        gson.fromJson(response.body.dataset, ChallengeData::class.java)
                    flow {
                        emit(FlowResult(challengeData, "", ""))
                    }
                } else {
                    flow {
                        emit(FlowResult(null, "", ""))
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
}
