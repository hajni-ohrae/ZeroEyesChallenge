package biz.ohrae.challenge_repo.ui.detail

import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                    val challengeData = gson.fromJson(response.body.dataset, ChallengeData::class.java)
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