package biz.ohrae.challenge_repo.model.user

import biz.ohrae.challenge_repo.model.detail.ChallengeData

data class PaymentHistoryData(
    val id: Int,
    val type: String,
    val amount: Int,
    val fee: Int,
    val paid_amount:Int,
    val created_date: String,
    val updated_date: String,
    val challenge: ChallengeData,
    val payment: PaymentData
) {

}

data class PaymentData(
    val id: String,
    val amount: String,
    val bank_name: String?
)