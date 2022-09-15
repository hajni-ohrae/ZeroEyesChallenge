package biz.ohrae.challenge.util

import androidx.compose.ui.graphics.Color
import biz.ohrae.challenge.model.state.ChallengeDetailStatus

val challengeDetailStatusMap = mapOf<String, ChallengeDetailStatus>(
    Pair("pending", ChallengeDetailStatus("모집대기", Color(0xffebfaf1), Color(0xff219653))),
    Pair("register", ChallengeDetailStatus("모집중", Color(0xffebfaf1), Color(0xff219653))),
    Pair("register_closed", ChallengeDetailStatus("모집종료", Color(0xfff3f8ff), Color(0xff4985f8))),
    Pair("opened", ChallengeDetailStatus("진행중", Color(0xfff3f8ff), Color(0xff4985f8))),
    Pair("finished", ChallengeDetailStatus("종료", Color(0xffdedede), Color(0xff6c6c6c))),
    Pair("canceled", ChallengeDetailStatus("취소", Color(0xffdedede), Color(0xff6c6c6c))),
)