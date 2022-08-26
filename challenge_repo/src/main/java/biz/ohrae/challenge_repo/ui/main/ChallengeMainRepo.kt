package biz.ohrae.challenge_repo.ui.main

import biz.ohrae.challenge.model.card.ChallengeData
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
        val response = apiService.getAllChallenge(accessToken.toString(), "all", "daily")

        when (response) {
            is NetworkResponse.Success -> {

            }
            else -> {
            }
        }

        return flow {
            emit(FlowResult(ChallengeData.mock(), "", ""))
        }
    }
}