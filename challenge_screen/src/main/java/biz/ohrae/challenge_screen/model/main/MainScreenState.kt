package biz.ohrae.challenge_screen.model.main

import biz.ohrae.challenge_repo.model.detail.ChallengeData

data class MainScreenState(
    var challengeList: MutableList<ChallengeData>? = null,
    val topBannerList: List<TopBannerData>? = null,
    val authCycleList: List<AuthCycle>? = null,
    val userChallengeList: List<ChallengeData>? = null,
    var challengePage: Int? = null
) {
    companion object {
        fun mock() = MainScreenState(
            challengeList = mutableListOf(
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock(),
                ChallengeData.mock()
            ),
            topBannerList = listOf(
                TopBannerData.mock(),
            ),
            authCycleList = listOf(
                AuthCycle("1주동안"),
                AuthCycle("2주동안"),
                AuthCycle("3주동안"),
                AuthCycle("4주동안")
            ),
        )
    }
}

data class TopBannerData(
    val content: String,
) {
    companion object {
        fun mock() = TopBannerData(
            content = "챌린지 이렇게\n이용해보세요",
        )
    }
}

data class AuthCycle(
    val cycle: String
)
