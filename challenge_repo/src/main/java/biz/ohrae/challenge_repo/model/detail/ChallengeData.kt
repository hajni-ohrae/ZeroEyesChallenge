package biz.ohrae.challenge_repo.model.detail

import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.model.verify.ChallengeVerification

data class ChallengeData(
    val id: String,
    val user_id: String? = null,
    var goal: String? = null,
    var subject: String? = null,
    var apply_start_date: String? = null,
    var apply_end_date: String? = null,
    var start_date: String? = null,
    var end_date: String? = null,
    val min_deposit_amount: Int = 0,
    val free_rewards: String? = null,
    val free_rewards_winners: String? = null,
    val free_rewards_offer_way: String? = null,
    var is_verification_photo: Int = 0,
    var is_verification_checkin: Int = 0,
    var is_verification_time: Int = 0,
    var is_adult_only: Int = 0,
    var age_limit_type: String? = null,
    val is_feed_open: Int = 0,
    val achievement_percent: Number = 0,
    val rewards_percent: Number = 0,
    val today: Int = 0,
    var period: Int = 0,
    var verification_period_type: String? = null,
//    daily | weekday | weekend | per_week
    var per_week: Int = 0,
    var imageFile: ImageFile? = null,
    val status: String,
    var caution: String? = null,
    val created_date: String,
    val updated_date: String,
    val inChallenge: List<InChallenge>? = null,
    val total_verification_cnt: Int,
    var verification_daily_staying_time: Int,
    val remaining_time: String? = null,
    val user: User? = null,
    val owner: User? = null,
    val is_cancelable: Int,
    val is_canceled: Int,
    val is_valid: Int,
    val summary: Summary? = null,
    val inChallengeLike : InChallengeLike? = null
) {
    companion object {
        fun mock(id: String = "2d4033ab-244b-47f3-84e9-6af72be39d9f") = ChallengeData(
            id = "2d4033ab-244b-47f3-84e9-6af72be39d9f",
            user_id = id,
            goal = "주 2회 자전거 타고 인증하기",
            subject = "",
            apply_start_date = "2022-09-13",
            apply_end_date = "2022-09-20",
            start_date = "2022-09-20",
            end_date = "2022-09-29",
            min_deposit_amount = 1000,
            free_rewards = "",
            free_rewards_winners = "",
            free_rewards_offer_way = "",
            is_verification_photo = 0,
            is_verification_checkin = 0,
            is_verification_time = 0,
            is_adult_only = 0,
            is_feed_open = 0,
            achievement_percent = 1,
            rewards_percent = 1,
            period = 1,
            verification_period_type = "daily",
            per_week = 1,
            status = "pending",
            created_date = "2022-09-20",
            updated_date = "2022-09-20",
            total_verification_cnt = 0,
            verification_daily_staying_time = 0,
            is_cancelable = 1,
            is_canceled = 0,
            is_valid = 0
        )
    }
}

data class Summary(
    val total_user_cnt: Int = 0,
    val attend_cnt: Int = 0,
    val achievement_cnt: Int = 0,
    val achievement_percent: Int = 0,
    val total_amount: Int? = 0,
    val average_amount: Int? = 0,
    val total_rewards_amount: Int? = 0,
    val per_rewards_amount:Int? = 0,
    val average_verification_time:String,
    val total_verification_time:String,
    val time_user_cnt:Int,
)

data class InChallenge(
    val id: Int = 0,
    val achievement_percent: String,
    val verification_cnt: Int,
    val today_verification_cnt: Int,
    val total_verification_count: Int,
    val total_verification_cnt: Int,
    val ranking: Int?,
    val refund_amount:Int,
    val deposit_amount: Int?,
    val rewards_amount:Int?,
    var verifications: List<ChallengeVerification>?,
    val verification_time:String?,
)

data class ImageFile(
    val id: Int,
    val type: String,
    var path: String,
    val thumbnail_path: String
)

data class InChallengeLike(
    val like: Int?,
)