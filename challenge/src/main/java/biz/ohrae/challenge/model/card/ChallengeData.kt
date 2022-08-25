package biz.ohrae.challenge.model.card

import biz.ohrae.challenge.model.list_item.ChallengeItemData

data class ChallengeData (
    val challengeList: List<ChallengeItemData>,
){
    companion object {
        fun mock() = ChallengeData(
            challengeList = listOf(
                ChallengeItemData.mock(),
                ChallengeItemData.mock(),
                ChallengeItemData.mock(),
                ChallengeItemData.mock(),
                ChallengeItemData.mock()
            ),
        )
    }
}