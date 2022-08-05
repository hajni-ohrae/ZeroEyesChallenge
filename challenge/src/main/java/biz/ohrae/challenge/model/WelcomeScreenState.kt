package biz.ohrae.challenge.model

data class WelcomeScreenState(
    val list: List<WelcomeData> = listOf(WelcomeData.mock1(), WelcomeData.mock2(), WelcomeData.mock3()),
    var currentPage: Int = 0
)

data class WelcomeData(
    val title: String,
    val content: String,
    val buttonName: String
) {
    companion object {
        fun mock1() = WelcomeData(
            title = "챌린지 해보실래요?",
            content = "챌린지에 참여하면 더욱 재미있는\n공부 경험을 할 수 있습니다",
            buttonName = "다음"
        )
        fun mock2() = WelcomeData(
            title = "더 나은 나를 즐겨보세요",
            content = "나의 랭킹과 챌린지 진도를 확인하고 \n친구와 함께 즐겨보세요",
            buttonName = "다음"
        )
        fun mock3() = WelcomeData(
            title = "리워즈도 준비했어요",
            content = "목표 달성해서 성취감도 얻고\n소소한 리워드도 챙겨가세요",
            buttonName = "확인"
        )
    }
}
