package biz.ohrae.challenge.model.card

import android.R.attr.x
import android.R.attr.y


data class ChallengesInParticipationData(
    val stage:String,
    val title: String,
    val count: Int,
    val maxPeople:Int,
)