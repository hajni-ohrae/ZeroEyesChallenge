package biz.ohrae.challenge.model.register

data class ChallengeData(
    val user_id: String? = null,
    val goal: String? = null,
    val subject: String? = null,
    val apply_start_date: String? = null,
    val apply_end_date: String? = null,
    val start_date: String? = null,
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
    val per_week: Int = 0,
    val image_path: String? = null,
) {
    companion object {
        fun mock(id: String = "b-DZuUzIs1") = ChallengeData(
            user_id = id,
            goal = "주 2회 자전거 타고 인증하기",
            subject = null,
            apply_start_date = "2022-09-06 12:00:00",
            apply_end_date = "2022-09-13 12:00:00",
            start_date = "2022-09-06 12:00:00",
            min_deposit_amount = 100,
            free_rewards = null,
            free_winners = null,
            free_rewards_offer_way = null,
            is_verification_photo = 0,
            is_verification_checkin = 0,
            is_verification_time = 0,
            is_adult_only = 0,
            is_feed_open = 0,
            achievement_percent = 0,
            rewards_percent = 0,
            period = 0,
            verification_period_type = null,
            per_week = 0,
            image_path = null

        )
    }

}