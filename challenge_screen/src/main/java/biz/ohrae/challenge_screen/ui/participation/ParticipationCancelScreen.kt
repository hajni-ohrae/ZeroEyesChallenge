package biz.ohrae.challenge_screen.ui.participation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel2
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.detail.ChallengeData


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ParticipationCancelScreen(
    challengeData: ChallengeData = ChallengeData.mock(),

    ) {
    Column() {
        Text(text = "챌린지 참여 취소하시겠습니까?", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "챌린지 참여를 취소하면 참여금이 환불되고 해당 챌린지에 참여할 수 없습니다",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = Color(0xff828282)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp,Color(0xffeeeeee)) ,
            elevation = 0.dp,) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)) {
                Text(text = "매일 6시간씩 한국사 공부", style = myTypography.w700, fontSize = dpToSp(dp = 16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                ChallengeDurationLabel2(dDay = "내일부터시작", week = "4주동안", numberOfTimes = "평일만")
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "챌린지 기간",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp), color = Color(0xff4f4f4f))
                    Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "인증방식",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp), color = Color(0xff4f4f4f))
                    Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "환불 예정 금액",style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
            Text(text = "원",style = myTypography.bold, fontSize = dpToSp(dp = 13.dp), color = Color(0xff4985f8))
        }
        Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
            FlatBottomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                text = "참여취소",
                onClick = { },
//                enabled =
            )
        }
    }
}