package biz.ohrae.challenge_repo.model.participation

data class PaidInfo(
    val cardName: String,
    val amount: Int,
    val rewardsAmount: Int,
) {
    companion object {
        fun mock() = PaidInfo(
            cardName = "카카오뱅크 카드",
            amount = 1000,
            rewardsAmount = 1000,
        )
    }
}
