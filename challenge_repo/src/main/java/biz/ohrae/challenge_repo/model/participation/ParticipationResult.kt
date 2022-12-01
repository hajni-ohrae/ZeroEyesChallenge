package biz.ohrae.challenge_repo.model.participation

data class ParticipationResult(
    val user_in_challenge_id: Int,
    var paid_amount: Int,
    val rewards_amount: Int,
    val total_deposit_amount: Int,
    var card_name: String?,
)
