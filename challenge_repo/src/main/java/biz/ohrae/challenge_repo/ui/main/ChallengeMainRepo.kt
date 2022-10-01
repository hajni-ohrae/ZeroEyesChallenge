package biz.ohrae.challenge_repo.ui.main

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ChallengeMainRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun getChallenges(
        paymentType: String = "",
        verificationPeriodType: String = ""
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response =
            apiService.getAllChallenge(accessToken.toString(), paymentType, verificationPeriodType)

        when (response) {
            is NetworkResponse.Success -> {
                val gson = Gson()
                val isSuccess = response.body.success
                if (isSuccess) {
                    val dataset = response.body.dataset
                    dataset?.let {

                        val dataSet: JsonElement = dataset?.getAsJsonArray("array")!!.asJsonArray

                        val listType = object : TypeToken<List<ChallengeData?>?>() {}.type
                        val challengeList = gson.fromJson<List<ChallengeData>>(dataSet, listType)

                        return flow {
                            emit(FlowResult(challengeList, "", ""))
                        }
                    } ?: run {
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

    suspend fun getUserChallengeList(): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response =
            apiService.getUserChallengeList(accessToken.toString())
        when (response) {
            is NetworkResponse.Success -> {
                val gson = Gson()
                val isSuccess = response.body.success
                if (isSuccess) {
                    val dataset = response.body.dataset
                    dataset?.let {

                        val dataSet: JsonElement = dataset?.getAsJsonArray("array")!!.asJsonArray

                        val listType = object : TypeToken<List<ChallengeData?>?>() {}.type
                        val userChallengeList = gson.fromJson<List<ChallengeData>>(dataSet, listType)
                        return flow {
                            emit(FlowResult(userChallengeList, "", ""))
                        }
                    } ?: run {
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
}