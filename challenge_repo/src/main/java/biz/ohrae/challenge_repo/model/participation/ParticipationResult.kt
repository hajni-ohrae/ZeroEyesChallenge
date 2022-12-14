package biz.ohrae.challenge_repo.model.participation

data class ParticipationResult(
    val user_in_challenge_id: Int,
    var paid_amount: Int,
    var rewards_amount: Int,
    val total_deposit_amount: Int,
    var card_name: String?
) {
    companion object {
        fun mock() = ParticipationResult(
            user_in_challenge_id = 1,
            paid_amount = 1000,
            rewards_amount = 1000,
            total_deposit_amount = 2000,
            card_name = "현대카드"
        )
    }
}
