package biz.ohrae.challenge_screen.model.user

import biz.ohrae.challenge_repo.model.detail.ChallengeData

data class UserChallengeListState(
    val userChallengeList: List<ChallengeData>? = null,
) {
    companion object {
        fun mock() = UserChallengeListState(
            userChallengeList = listOf(
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock()
            ),
        )
    }
}