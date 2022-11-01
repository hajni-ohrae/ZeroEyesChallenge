package biz.ohrae.challenge_screen.model.main

import biz.ohrae.challenge.model.filter.FilterItem

data class FilterState(
    val filterItem: List<FilterItem>,
    val userChallengeFilter: List<FilterItem>,
    val certifiedList: List<FilterItem>,
    val periodList: List<FilterItem>,
    val etcList: List<FilterItem>,

    var selectFilterType: String = "",
    var selectUserChallengeType: String = "",
    var selectVerificationPeriodType: String = "",
    var selectPeriod: String = "",
    var selectPreWeek: String = "",
    var selectIsAdultOnly: String = "",

    ) {
    companion object {
        fun mock() = FilterState(
            filterItem = listOf(
                FilterItem("전체", "all"),
                FilterItem("유료", "paid"),
                FilterItem("무료", "free"),
            ),
            userChallengeFilter = listOf(
                FilterItem("전체", "all"),
                FilterItem("모집중", "pending"),
                FilterItem("진행중", "opened"),
                FilterItem("완료", "finished"),
            ),
            selectFilterType = "all",
            selectUserChallengeType = "all",
            certifiedList = listOf(
                FilterItem("매일", "daily"),
                FilterItem("평일만", "weekday"),
                FilterItem("주말만", "weekend"),
                FilterItem("주1회", "1"),
                FilterItem("주2회", "2"),
                FilterItem("주3회", "3"),
                FilterItem("주4회", "4"),
                FilterItem("주5회", "5"),
                FilterItem("주6회", "6"),
            ),
            periodList = listOf(
                FilterItem("주1회", "1"),
                FilterItem("주2회", "2"),
                FilterItem("주3회", "3"),
            ),
            etcList = listOf(
                FilterItem("18세 미만 참여불가", "1"),
            )
        )
    }
}