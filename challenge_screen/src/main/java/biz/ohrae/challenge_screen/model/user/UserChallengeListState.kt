package biz.ohrae.challenge_screen.model.user

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import biz.ohrae.challenge_repo.model.detail.ChallengeData

data class UserChallengeListState(
    var userChallengeList: SnapshotStateList<ChallengeData>? = null,
    var userChallengePage: Int? = null,

    ) {
    companion object {
        fun mock() = UserChallengeListState(
            userChallengeList = mutableStateListOf(
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock()
            ),
        )
    }
}