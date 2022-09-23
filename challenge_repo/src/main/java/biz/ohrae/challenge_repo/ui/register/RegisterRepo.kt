package biz.ohrae.challenge_repo.ui.register

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun createChallenge(userId: String, challengeData: ChallengeData): Flow<FlowResult> {
        val jsonObject = JsonObject()
        val response = apiService.createChallenge(jsonObject)
        jsonObject.addProperty("user_id", userId)
        jsonObject.addProperty("goal", challengeData.goal)
        jsonObject.addProperty("subject", challengeData.subject)
        jsonObject.addProperty("apply_start_date", challengeData.apply_start_date)
        jsonObject.addProperty("apply_end_date", challengeData.apply_end_date)
        jsonObject.addProperty("start_date", challengeData.start_date)
        jsonObject.addProperty("min_deposit_amount", challengeData.min_deposit_amount)
        jsonObject.addProperty("free_rewards", challengeData.free_rewards)
        jsonObject.addProperty("free_winners", challengeData.free_winners)
        jsonObject.addProperty("free_rewards_offer_way", challengeData.free_rewards_offer_way)
        jsonObject.addProperty("is_verification_photo", challengeData.is_verification_photo)
        jsonObject.addProperty("is_verification_checkin", challengeData.is_verification_checkin)
        jsonObject.addProperty("is_verification_time", challengeData.is_verification_time)
        jsonObject.addProperty("is_adult_only", challengeData.is_adult_only)
        jsonObject.addProperty("is_feed_open", challengeData.is_feed_open)
        jsonObject.addProperty("achievement_percent", challengeData.achievement_percent)
        jsonObject.addProperty("rewards_percent", challengeData.rewards_percent)
        jsonObject.addProperty("period", challengeData.period)
        jsonObject.addProperty("verification_period_type", challengeData.verification_period_type)
        jsonObject.addProperty("per_week", challengeData.per_week)
        jsonObject.addProperty("image_path", challengeData.image_path)
        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                return if (isSuccess) {
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