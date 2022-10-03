package biz.ohrae.challenge_repo.model.user

import biz.ohrae.challenge_repo.model.detail.ChallengeData

data class UserChallengeData(
    val id: String,
    val type: String? = null,
    val name: String? = null,
    val nickname: String? = null,
    val phone_number: String? = null,
    val birth_date: String? = null,
    val image_path: String? = null,
    val is_blocked: Int = 0,
    val rewards_amount: Int = 0,
    val total_deposit_amount: Int = 0,
    val total_refund_amount: Int = 0,
    val total_transfer_amount: Int = 0,
    val total_rewards_amount: Int = 0,
    val total_report_count: Int = 0,
    val total_redcard_count: Int = 0,
    val created_date: String? = null,
    val updated_date: String? = null,
    val inChallenge: List<Any>?,
) {
//    companion object {
//        fun mock() = UserChallengeData(
//            id = "2d4033ab-244b-47f3-84e9-6af72be39d9f",
//            type = "",
//            inChallenge = listOf(),
//            )
//    }

}