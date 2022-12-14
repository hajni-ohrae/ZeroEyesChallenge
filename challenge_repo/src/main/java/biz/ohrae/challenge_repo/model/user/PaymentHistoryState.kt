package biz.ohrae.challenge_repo.model.user

import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.participation.PaidInfo

data class PaymentHistoryData(
    val id: Int = 0,
    val type: String = "",
    val amount: Int = 0,
    val fee: Int = 0,
    val paid_amount: Int = 0,
    val rewards_amount: Int = 0,
    val created_date: String = "",
    val updated_date: String = "",
    val challenge: ChallengeData,
    val payment: PaymentData
) {

}

data class PaymentData(
    val id: String,
    val amount: String,
    val card_name: String?
) {
}