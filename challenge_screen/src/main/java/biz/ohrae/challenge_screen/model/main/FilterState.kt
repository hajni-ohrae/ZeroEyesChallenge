package biz.ohrae.challenge_screen.model.main

import biz.ohrae.challenge.model.filter.FilterItem

data class FilterState(
    val filterItem: List<FilterItem>,
    var selectFilterType: String = ""
) {

    companion object {
        fun mock() = FilterState(
            filterItem = listOf(
                FilterItem("전체", "all"),
                FilterItem("유료", "paid"),
                FilterItem("무료", "free"),
            ),
            selectFilterType = "all",
        )
    }
}