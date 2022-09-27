package biz.ohrae.challenge_screen.ui.participation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel2
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.detail.ChallengeData

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ParticipationCompletedScreen(
    challengeData: ChallengeData = ChallengeData.mock(),) {
    Column(Modifier.background(DefaultWhite)) {
        Column(modifier = Modifier.padding(24.dp, 0.dp)) {
            Text(
                text = "챌린지 참여가 완료되었습니다",
                style = myTypography.w500,
                fontSize = dpToSp(dp = 20.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                backgroundColor = DefaultWhite,
                border = BorderStroke(1.dp, Color(0xffeeeeee)) ,
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "참가인원",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp), color = Color(0xff4f4f4f))
                        Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}",style = myTypography.bold, fontSize = dpToSp(dp = 13.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "누적 참여금",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp), color = Color(0xff4f4f4f))
                        Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}",style = myTypography.bold, fontSize = dpToSp(dp = 13.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "평균 참여금",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp), color = Color(0xff4f4f4f))
                        Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp))
                    }
                }

            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "결제내역", style = myTypography.bold, fontSize = dpToSp(dp = 18.dp))

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "결제 수단",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp), color = Color(0xff4f4f4f))
                Text(text = "현대카드 (신용카드)",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "총 결제 금액",style = myTypography.w500, fontSize = dpToSp(dp = 13.dp), color = Color(0xff4f4f4f))
                Text(text = "원",style = myTypography.bold, fontSize = dpToSp(dp = 13.dp), color = Color(0xff4985f8))
            }
        }
        Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
            FlatDoubleButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                leftText = "결제상세 보기",
                rightText = "확인"
            )
        }
    }
}
