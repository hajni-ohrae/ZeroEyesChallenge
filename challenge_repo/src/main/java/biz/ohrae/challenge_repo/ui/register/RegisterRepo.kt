package biz.ohrae.challenge_repo.ui.register

import biz.ohrae.challenge.model.MainScreenState
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
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
    suspend fun createChallenge(challengeData: ChallengeData) {
        val jsonObject = JsonObject()
        val response = apiService.createChallenge(jsonObject)
        jsonObject.addProperty("customer_id", challengeData.user_id)
        jsonObject.addProperty("customer_id", challengeData.goal)
        jsonObject.addProperty("customer_id", challengeData.subject)
        jsonObject.addProperty("customer_id", challengeData.apply_start_date)
        jsonObject.addProperty("customer_id", challengeData.apply_end_date)
        jsonObject.addProperty("customer_id", challengeData.start_date)
        jsonObject.addProperty("customer_id", challengeData.min_deposit_amount)
        jsonObject.addProperty("customer_id", challengeData.free_rewards)
        jsonObject.addProperty("customer_id", challengeData.free_winners)
        jsonObject.addProperty("customer_id", challengeData.free_rewards_offer_way)
        jsonObject.addProperty("customer_id", challengeData.is_verification_photo)
        jsonObject.addProperty("is_verification_checkin", challengeData.is_verification_checkin)
        jsonObject.addProperty("is_verification_time", challengeData.is_verification_time)
        jsonObject.addProperty("is_adult_only", challengeData.is_adult_only)
        jsonObject.addProperty("is_feed_open", challengeData.is_feed_open)
        jsonObject.addProperty("achievement_percent", challengeData.achievement_percent)
        jsonObject.addProperty("rewards_percent", challengeData.rewards_percent)
        jsonObject.addProperty("period", challengeData.period)
        jsonObject.addProperty("verification_period_type", challengeData.verification_period_type)
        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                if (isSuccess) {

                } else {

                }
            }
            else -> {
            }
        }
    }
}