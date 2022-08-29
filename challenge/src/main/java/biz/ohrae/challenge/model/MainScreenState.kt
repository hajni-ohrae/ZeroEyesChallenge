package biz.ohrae.challenge.model

import biz.ohrae.challenge.model.list_item.ChallengeItemData

data class MainScreenState(
    val challengeList: List<ChallengeItemData>,
    val topBannerList:List<TopBannerData>
    ) {
    companion object {
        fun mock() = MainScreenState(
            challengeList = listOf(
                ChallengeItemData.mock(),
                ChallengeItemData.mock(),
                ChallengeItemData.mock(),
                ChallengeItemData.mock(),
                ChallengeItemData.mock()
            ),
            topBannerList = listOf(
                TopBannerData.mock(),
                TopBannerData.mock(),
                TopBannerData.mock(),
            )
        )
    }
}

data class TopBannerData(
    val content: String,
) {
    companion object {
        fun mock() = TopBannerData(
            content = "챌린지에 참여하면 더욱 재미있는\n공부 경험을 할 수 있습니다",
        )
    }
}

//fun mock1() = MainScreenState(
//    topBannerList = listOf(
//        TopBannerData.mock(),
//        TopBannerData.mock(),
//        TopBannerData.mock(),
//        TopBannerData.mock(),
//        TopBannerData.mock()
//    ),
//)
//}
//}
//}
//
//data class TopBannerData(
//    val content: String,
//) {
//    companion object {
//        fun mock() = TopBannerData(
//            content = "챌린지에 참여하면 더욱 재미있는\n공부 경험을 할 수 있습니다",
//        )
//    }
//}
//
//}