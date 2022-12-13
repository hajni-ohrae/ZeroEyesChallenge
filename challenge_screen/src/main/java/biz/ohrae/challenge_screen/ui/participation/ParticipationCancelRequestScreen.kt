package biz.ohrae.challenge_screen.ui.participation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel2
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeVerificationPeriodMap
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.prefs.Utils
import java.text.NumberFormat
import java.util.*

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ParticipationCancelRequestScreen(
    challengeData: ChallengeData = ChallengeData.mock(),
    clickListener: ParticipationClickListener? = null,
) {

    val isFree by remember {
        mutableStateOf(challengeData.min_deposit_amount <= 0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultWhite)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp, 0.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "챌린지 참여 취소하시겠습니까?",
                style = myTypography.w500,
                fontSize = dpToSp(dp = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (isFree) {
                Text(
                    text = "챌린지 참여를 취소하면 해당 챌린지에 재참여할\n수 없습니다",
                    style = myTypography.default,
                    color = Color(0xff6c6c6c),
                    fontSize = dpToSp(dp = 16.dp),
                    lineHeight = dpToSp(dp = 22.dp)
                )
            } else {
                Text(
                    text = "챌린지 참여를 취소하면 참여금이 환불되고\n해당 챌린지에 참여할 수 없습니다",
                    style = myTypography.default,
                    color = Color(0xff6c6c6c),
                    fontSize = dpToSp(dp = 16.dp),
                    lineHeight = dpToSp(dp = 22.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            ParticipationDetailCardForCancel(challengeData = challengeData)
            Spacer(modifier = Modifier.height(32.dp))
            if (challengeData.min_deposit_amount > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "환불 예정 금액",
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 16.dp),
                        color = TextBlack
                    )
                    Text(
                        text = "${Utils.numberFormat(challengeData.inChallenge?.get(0)?.deposit_amount)}원",
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color(0xff4985f8)
                    )
                }
            }
        }
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            text = "참여취소",
            onClick = {
                clickListener?.onClickCancelParticipation(isFree)
            }
        )
    }
}

@Composable
fun ParticipationDetailCardForCancel(challengeData: ChallengeData) {
    val startDate by remember { mutableStateOf(Utils.convertDate6(challengeData.start_date.toString())) }
    val endDate by remember { mutableStateOf(Utils.convertDate6(challengeData.end_date.toString())) }
    val authType by remember { mutableStateOf(getAuthText(challengeData)) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xffeeeeee)),
        elevation = 0.dp,
        backgroundColor = DefaultWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = challengeData.goal.toString(),
                style = myTypography.w700,
                fontSize = dpToSp(dp = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            DurationLabel(challengeData)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "챌린지 기간",
                    style = myTypography.w500,
                    fontSize = dpToSp(dp = 13.dp),
                    color = Color(0xff4f4f4f)
                )
                Text(
                    text = "$startDate ~ $endDate",
                    style = myTypography.w500,
                    fontSize = dpToSp(dp = 13.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "인증방식",
                    style = myTypography.w500,
                    fontSize = dpToSp(dp = 13.dp),
                    color = Color(0xff4f4f4f)
                )
                Text(
                    text = authType,
                    style = myTypography.w500,
                    fontSize = dpToSp(dp = 13.dp)
                )
            }
        }
    }
}

@Composable
private fun DurationLabel(challengeData: ChallengeData) {
    val day by remember { mutableStateOf(Utils.getRemainTimeDays(challengeData.start_date.toString())) }
    val dayType by remember { mutableStateOf(challengeVerificationPeriodMap[challengeData.verification_period_type]) }

    ChallengeDurationLabel2(
        dDay = day,
        week = "${challengeData.period}주동안",
        numberOfTimes = if (dayType.isNullOrEmpty()) "주${challengeData.per_week}회 인증" else dayType.toString()
    )
}

private fun Long?.formatWithComma(): String =
    NumberFormat.getNumberInstance(Locale.US).format(this ?: 0)

private fun getAuthText(challengeData: ChallengeData): String {
    return if (challengeData.is_verification_photo == 1) {
        "사진 인증"
    } else if (challengeData.is_verification_time == 1) {
        "이용시간 인증(자동 인증)"
    } else if (challengeData.is_verification_checkin == 1) {
        "출석 인증(자동 인증)"
    } else {
        "기타 인증"
    }
}