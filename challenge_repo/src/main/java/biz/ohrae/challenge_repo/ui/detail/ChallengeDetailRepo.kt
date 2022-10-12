package biz.ohrae.challenge_repo.ui.detail

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.model.verify.VerifyData
import biz.ohrae.challenge_repo.model.verify.VerifyListState
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
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
            apiService.getUsersByChallenge(accessToken.toString(), page, count, challengeId)

        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val dataSet = response.body.dataset?.asJsonObject
                    val array = dataSet?.get("array")?.asJsonArray

                    val listType = object : TypeToken<List<User?>?>() {}.type
                    val challengerList =
                        gson.fromJson<List<ChallengeData>>(array, listType)

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
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }

    suspend fun verifyChallenge(
        challengeId: String,
        type: String,
        content: String,
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val userId = prefs.getUserData()?.id

        val jsonObject = JsonObject()
        jsonObject.addProperty("challenge_id", challengeId)
        jsonObject.addProperty("user_id", userId)
        jsonObject.addProperty("type", type)
        jsonObject.addProperty("comment", content)

        val response = apiService.verify(accessToken.toString(), jsonObject)
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    flow {
                        emit(FlowResult(null, "", ""))
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

    suspend fun getVerifyList(
        challengeId: String, order: String, isMine: String
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token

        val jsonObject = JsonObject()
        val filter = JsonObject()
        filter.addProperty("order", order)
        filter.addProperty("is_mine", isMine)
        jsonObject.add("filter", filter)
        val response =
            apiService.getVerifyList(challengeId, accessToken.toString(), body = jsonObject, 1, 10)
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val dataSet = response.body.dataset?.asJsonObject
                    val array = dataSet?.get("array")?.asJsonArray


                    val listType = object : TypeToken<List<VerifyData?>?>() {}.type
                    val verifyListState = gson.fromJson<List<VerifyListState>>(array, listType)

                    flow {
                        emit(FlowResult(verifyListState, "", ""))
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

}