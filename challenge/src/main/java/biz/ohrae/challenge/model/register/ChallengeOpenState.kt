package biz.ohrae.challenge.model.register

import biz.ohrae.challenge.model.list_item.ChallengeItemData

data class ChallengeOpenState(
    val authCycleList:List<AuthCycle>,
    val authFrequencyList :List<AuthFrequency>,
) {
    companion object {
        fun mock() = ChallengeOpenState(
            authCycleList = listOf(
                AuthCycle("1주동안"),
                AuthCycle("2주동안"),
                AuthCycle("3주동안"),
                AuthCycle("4주동안")
            ),
            authFrequencyList = listOf(
                AuthFrequency("매일인증"),
                AuthFrequency("평일만 인증(월,화,수,목,금"),
                AuthFrequency("주말만 인증 (토,일)"),
                AuthFrequency("주 1회 인증"),
                AuthFrequency("주 2회 인증"),
                AuthFrequency("주 3회 인증"),
                AuthFrequency("주 4회 인증"),
                AuthFrequency("주 5회 인증"),
                AuthFrequency("주 6회 인증"),
            )
        )
    }
}
data class AuthCycle(
    val cycle:String
)
data class AuthFrequency(
    val frequency:String
)