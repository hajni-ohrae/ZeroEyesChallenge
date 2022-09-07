package biz.ohrae.challenge_repo.ui.main

import biz.ohrae.challenge.model.MainScreenState
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChallengeMainRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun getChallenges(): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response = apiService.getAllChallenge(accessToken.toString(), "", "")

        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                if (isSuccess) {
                    val dataset = response.body.dataset
                    dataset?.let {
                        val data = it[0].asJsonObject
                        val challengeList = Gson().fromJson(data, ChallengeData::class.java)
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
            }
        }

        return flow {
            emit(FlowResult(MainScreenState.mock(), "", ""))
        }
    }
}