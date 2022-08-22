package biz.ohrae.challenge.ui.components.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.card.ChallengeItemData
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel2
import biz.ohrae.challenge.ui.theme.dpToSp


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ChallengeDetailsInfoGallery() {
    Column() {
        ChallengeDetailsInfo()
    }
}

@Composable
fun ChallengeDetailsInfo(challengeItemData: ChallengeItemData = ChallengeItemData.mock()) {
    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xffeeeeee)),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = challengeItemData.title)
            ChallengeDurationLabel2(
                modifier = Modifier.fillMaxWidth().padding(0.dp,16.dp),
                week = challengeItemData.week, numberOfTimes = challengeItemData.numberOfTimes, dDay = challengeItemData.dDay
            )
            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "챌린지 기간")
                Text(text = "${challengeItemData.startDate} ~ ${challengeItemData.endDate}", fontSize = dpToSp(dp = 16.dp))
            }
            Row(modifier = Modifier.fillMaxWidth().padding(0.dp,16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "인증방식")
                Text(text = "이용시간 인증")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "참가인원")
                Text(text = "${challengeItemData.personnel}명")
            }
            Row(modifier = Modifier.fillMaxWidth().padding(0.dp,16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "누적 참여금")
                Text(text = "")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "평균 참여금")
                Text(text = "")
            }
        }

    }
}