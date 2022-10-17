package biz.ohrae.challenge_screen.model.user

import biz.ohrae.challenge_repo.model.detail.ChallengeData

data class UserChallengeListState(
    var userChallengeList: MutableList<ChallengeData>? = null,
) {
    companion object {
        fun mock() = UserChallengeListState(
            userChallengeList = mutableListOf(
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock()
            ),
        )
    }
}