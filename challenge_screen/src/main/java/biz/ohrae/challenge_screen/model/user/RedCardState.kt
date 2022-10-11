package biz.ohrae.challenge_screen.model.user

import biz.ohrae.challenge_repo.model.detail.ChallengeData


data class RedCardListState(
    val redCardList: List<RedCard>? = null
) {
    companion object {

    }
}

data class RedCard(
    val id: Int,
    val reason: String,
    val is_valid: Int,
    val canceled_reason: String,
    val canceled_date: String,
    val created_date: String,
    val update_date: String,
)