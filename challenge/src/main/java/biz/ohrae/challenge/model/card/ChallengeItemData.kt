package biz.ohrae.challenge.model.card

import java.util.ArrayList

data class ChallengeItemData(
    val title: String,
    val userName: String,
    val dDay: String,
    val week: String,
    val numberOfTimes: String,
    val personnel:Int = 0,
)
