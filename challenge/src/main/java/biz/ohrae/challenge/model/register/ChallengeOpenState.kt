package biz.ohrae.challenge.model.register

import biz.ohrae.challenge.model.list_item.ChallengeItemData

data class ChallengeOpenState(
    val authCycleList: List<String>,
    val authFrequencyList: List<String>,
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
            )
        )
    }
}