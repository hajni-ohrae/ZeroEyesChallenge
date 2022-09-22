package biz.ohrae.challenge.model.list_item

import biz.ohrae.challenge.model.state.ChallengeDetailStatus

data class ChallengeItemData(
    val id: String,
    val title: String,
    val userName: String,
    val dDay: String,
    val week: String,
    val numberOfTimes: String,
    val personnel: Int = 0,
    val startDate: String,
    val endDate: String,
    val state: ChallengeDetailStatus
) {
    companion object {
        fun mock() = ChallengeItemData(
            id = "1",
            title = "매일 6시간씩 한국사 공부",
            userName = "하진",
            dDay = "오늘부터 시작",
            week = "1주동안",
            numberOfTimes = "주말만",
            personnel = 17,
            startDate = "4월11일(월)",
            endDate = "4월24일(일)",
            state = ChallengeDetailStatus.mock()
        )
    }
}
