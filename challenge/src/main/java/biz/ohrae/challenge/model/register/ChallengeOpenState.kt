package biz.ohrae.challenge.model.register

data class ChallengeOpenState(
    val authCycleList: List<String>,
    val authFrequencyList: List<String>,
    val challengeRadioOptions: List<RadioOptions>
) {
    companion object {
        fun mock() = ChallengeOpenState(
            authCycleList = listOf(
                "1주동안",
                "2주동안",
                "3주동안",
                "4주동안"
            ),
            authFrequencyList = listOf(
                "매일인증",
                "평일만 인증(월,화,수,목,금",
                "주말만 인증 (토,일)",
                "주 1회 인증",
                "주 2회 인증",
                "주 3회 인증",
                "주 4회 인증",
                "주 5회 인증",
                "주 6회 인증",
            ),
            challengeRadioOptions = listOf(
                RadioOptions("사진인증", "photo","즉석 촬영으로만 인증이 가능합니다"),
                RadioOptions("출석인증 (자동)", "checkIn","입실 시 자동 인증됩니다"),
                RadioOptions("이용시간 인증 (자동)", "time","입실~퇴실 시간으로 자동 인증됩니다"),
            )
        )
    }
}
data class RadioOptions(
    val radioTitle: String,
    val radioTitleEn:String,
    val radioContent: String,
)