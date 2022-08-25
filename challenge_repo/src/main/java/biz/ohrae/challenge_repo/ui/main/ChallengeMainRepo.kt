package biz.ohrae.challenge_repo.ui.main

import biz.ohrae.challenge.model.card.ChallengeData
import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.model.FlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChallengeMainRepo @Inject constructor(private val apiService: ApiService) {
    suspend fun getChallenges(): Flow<FlowResult> {
        return flow {
            emit(FlowResult(ChallengeData.mock(), "", ""))
        }
    }
}