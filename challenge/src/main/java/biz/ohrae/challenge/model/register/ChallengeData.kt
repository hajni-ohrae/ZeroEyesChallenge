package biz.ohrae.challenge.model.register

data class ChallengeData(
    val user_id: String,
    val goal: String,
    val subject: String,
    val apply_start_date: String,

    val apply_end_date: String,

    val start_date: String,

    val min_deposit_amount: Int,
    val free_rewards: String,
    val free_winners: String,
    val free_rewards_offer_way: String,
    val is_verification_photo: Int,
    val is_verification_checkin: Int,
    val is_verification_time: Int,

    val is_adult_only: Int,
    val is_feed_open: Int,
    val achievement_percent: Int,
    val rewards_percent: Int,
    val period: Int,
    val verification_period_type: String,
//    daily | weekday | weekend | per_week

    val per_week: Int,
    val image_path: String,
) {

}