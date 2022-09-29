package biz.ohrae.challenge_screen.model.register

import biz.ohrae.challenge.ui.components.dropdown.DropDownItem

data class ChallengeOpenState(
    var authCycleList: List<DropDownItem>,
    val authFrequencyList: List<DropDownItem>,
    val challengeRadioOptions: List<RadioOptions>,
) {
    companion object {
        fun mock() = ChallengeOpenState(
            authCycleList = listOf(
                DropDownItem("1주동안","1"),
                DropDownItem("2주동안","2"),
                DropDownItem("3주동안","3"),
                DropDownItem("4주동안","4"),
            ),
            authFrequencyList = listOf(
                DropDownItem("매일 인증","daily"),
                DropDownItem("평일만 인증(월,화,수,목,금)","weekday"),
                DropDownItem("주말만 인증 (토,일)","weekend"),
                DropDownItem("주 1회 인증","1"),
                DropDownItem("주 2회 인증","2"),
                DropDownItem("주 3회 인증","3"),
                DropDownItem("주 4회 인증","4"),
                DropDownItem("주 5회 인증","5"),
                DropDownItem("주 6회 인증","6"),
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