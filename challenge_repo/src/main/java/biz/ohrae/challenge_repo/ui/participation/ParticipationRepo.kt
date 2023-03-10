package biz.ohrae.challenge_repo.ui.participation

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.participation.ParticipationResult
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_repo.util.prefs.Utils
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ParticipationRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun registerChallenge(challengeData: ChallengeData, paidAmount: Int, rewardsAmount: Int, depositAmount: Int): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val userId = prefs.getUserData()?.id
        val jsonObject = JsonObject()
        val challengeType = Utils.getAuthTypeEnglish(challengeData)

//        jsonObject.addProperty("user_id", userId)
        jsonObject.addProperty("challenge_id", challengeData.id)
        jsonObject.addProperty("verification_type", challengeType)
        jsonObject.addProperty("paid_amount", paidAmount)
        jsonObject.addProperty("rewards_amount", rewardsAmount)
//        jsonObject.addProperty("deposit_amount", depositAmount)

        val response = apiService.registerChallenge(accessToken.toString(),jsonObject)
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val participationResult = gson.fromJson(response.body.dataset, ParticipationResult::class.java)
                    flow {
                        emit(FlowResult(participationResult, "", ""))
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

    suspend fun requestPayment(): Flow<FlowResult> {
        val userId = prefs.getUserData()?.id
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", userId)
        jsonObject.addProperty("challenge_id", userId)
        jsonObject.addProperty("paid_amount", userId)
        jsonObject.addProperty("rewards_amount", userId)
        jsonObject.addProperty("pay_method", "card")
        jsonObject.addProperty("pg", "nice")

        val response = apiService.requestPayment(jsonObject)
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

    suspend fun cancelChallenge(challengeData: ChallengeData): Flow<FlowResult> {
        val user = prefs.getUserData()
        val challengeId = challengeData.id
        val accessToken = user?.access_token.toString()

        val response = apiService.cancelChallenge(accessToken, challengeId)
        when (response) {
            is NetworkResponse.Success -> {
                return if (response.body.success) {
                    val cancelResult = gson.fromJson(response.body.dataset, ParticipationResult::class.java)
                    flow {
                        emit(FlowResult(cancelResult, "", ""))
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

    suspend fun checkChallenge(challengeData: ChallengeData): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val jsonObject = JsonObject()
        val challengeType = Utils.getAuthTypeEnglish(challengeData)

        jsonObject.addProperty("challenge_id", challengeData.id)
        jsonObject.addProperty("verification_type", challengeType)

        val response = apiService.checkDuplicateChallenge(accessToken.toString(), jsonObject)
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
}