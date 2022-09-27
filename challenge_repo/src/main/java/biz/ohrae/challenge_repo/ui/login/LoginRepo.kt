package biz.ohrae.challenge_repo.ui.login

import biz.ohrae.challenge_repo.data.remote.ApiServiceForBase
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.login.Auth
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class LoginRepo @Inject constructor(
    private val apiService: ApiServiceForBase,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun auth(deviceUid: String, phoneNumber: String): Flow<FlowResult> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("phone_number", phoneNumber)
        jsonObject.addProperty("os", "android")
        jsonObject.addProperty("app_version", "10.0")
        jsonObject.addProperty("shop_id", "mooin_test")

        val response = apiService.auth("mooin", deviceUid, jsonObject)
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val listType = object : TypeToken<ArrayList<Auth?>?>() {}.type
                    val auth: List<Auth> = gson.fromJson<List<Auth>>(response.body.dataset, listType)

                    flow {
                        emit(FlowResult(auth[0], "", ""))
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

    suspend fun authCheck(phoneNumber: String, authNumber: String): Flow<FlowResult> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("phone_number", phoneNumber)
        jsonObject.addProperty("auth_number", authNumber)
        val response = apiService.authCheck("mooin", jsonObject)

        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    flow {
                        val dataSet = response.body.dataset
                        if (dataSet != null && dataSet.size() > 0) {
                            val jsonObject = dataSet[0].asJsonObject
                            val customerId = jsonObject.get("id")
                            val accessToken = jsonObject.get("access_token")

                            Timber.e("customerId : $customerId")
                            Timber.e("accessToken : $accessToken")
                            if (customerId != null) {
                                prefs.setZeCustomerId(customerId.asString)
                            }
                            if (accessToken != null) {
                                prefs.setZeAccessToken(accessToken.asString)
                            }
                        }

                        emit(FlowResult(true, "", ""))
                    }
                } else {
                    flow {
                        emit(FlowResult(false, "", ""))
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

    suspend fun relate(): Flow<FlowResult> {
        val customerId = prefs.getZeCustomerId()
        val accessToken = prefs.getZeAccessToken()
        val response = apiService.relate("mooin", access_token = accessToken, customer_id = customerId)

        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val listType = object : TypeToken<ArrayList<String?>?>() {}.type
                    val serviceIds: List<String> = gson.fromJson<List<String>>(response.body.dataset, listType)

                    flow {
                        emit(FlowResult(serviceIds, "", ""))
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