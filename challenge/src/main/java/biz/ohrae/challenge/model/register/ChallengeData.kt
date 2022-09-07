package biz.ohrae.challenge.model.register

data class ChallengeData(
    val user_id: String? = null,
    var goal: String? = null,
    var subject: String? = null,
    val apply_start_date: String? = null,
    val apply_end_date: String? = null,
    var start_date: String? = null,
    val min_deposit_amount: Int = 0,
    val free_rewards: String? = null,
    val free_winners: String? = null,
    val free_rewards_offer_way: String? = null,
    var is_verification_photo: Int = 0,
    var is_verification_checkin: Int = 0,
    var is_verification_time: Int = 0,
    val is_adult_only: Int = 0,
    val is_feed_open: Int = 0,
    val achievement_percent: Int = 0,
    val rewards_percent: Int = 0,
    val period: Int = 0,
    var verification_period_type: String? = null,
//    daily | weekday | weekend | per_week
    var per_week: Int = 0,
    var image_path: String,
) {
    companion object {
        fun mock(id: String = "2d4033ab-244b-47f3-84e9-6af72be39d9f") = ChallengeData(
            user_id = id,
            goal = "주 2회 자전거 타고 인증하기",
            subject = "test",
            apply_start_date = "2022-09-13",
            apply_end_date = "2022-09-20",
            start_date = "2022-09-20",
            min_deposit_amount = 1000,
            free_rewards = "",
            free_winners = "",
            free_rewards_offer_way = "",
            is_verification_photo = 1,
            is_verification_checkin = 0,
            is_verification_time = 0,
            is_adult_only = 1,
            is_feed_open = 0,
            achievement_percent = 1,
            rewards_percent = 1,
            period = 3,
            verification_period_type = "daily",
            per_week = 3,
            image_path = "daily"

        )
    }

}