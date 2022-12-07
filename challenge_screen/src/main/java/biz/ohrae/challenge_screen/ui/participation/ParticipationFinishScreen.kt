package biz.ohrae.challenge_screen.ui.participation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel2
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeVerificationPeriodMap
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.participation.PaidInfo
import biz.ohrae.challenge_repo.model.participation.ParticipationResult
import biz.ohrae.challenge_repo.util.prefs.Utils
import java.text.NumberFormat
import java.util.*

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ParticipationFinishScreen(
    challengeData: ChallengeData = ChallengeData.mock(),
    participationResult: ParticipationResult? = null,
    paidInfo: PaidInfo? = null,
    clickListener: ParticipationClickListener? = null,
) {
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
                text = "챌린지 참여가 완료되었습니다",
                style = myTypography.w500,
                fontSize = dpToSp(dp = 20.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            ParticipationDetailCard(challengeData = challengeData)
            Spacer(modifier = Modifier.height(24.dp))
            if (challengeData.min_deposit_amount > 0) {
                Text(text = "결제 내역", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    var cardName = "리워즈"
                    paidInfo?.let {
                        cardName = it.cardName
                        if (it.rewardsAmount > 0) {
                            if (cardName != "리워즈") {
                                cardName += " + 리워즈"
                            }
                        }
                    }

                    Text(
                        text = "결제 수단",
                        style = myTypography.default,
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color(0xff6c6c6c)
                    )
                    Text(
                        text = cardName,
                        style = myTypography.default,
                        fontSize = dpToSp(dp = 16.dp),
                        color = TextBlack
                    )
                }
                if (participationResult != null && participationResult.rewards_amount > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "리워즈 사용",
                            style = myTypography.default,
                            fontSize = dpToSp(dp = 16.dp),
                            color = Color(0xff6c6c6c)
                        )
                        Text(
                            text = "${Utils.numberFormat(participationResult.rewards_amount)}원",
                            style = myTypography.bold,
                            fontSize = dpToSp(dp = 16.dp),
                            color = Color(0xff4985f8)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "총 결제 금액",
                        style = myTypography.default,
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color(0xff6c6c6c)
                    )
                    Text(
                        text = "${Utils.numberFormat(participationResult?.total_deposit_amount)}원",
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color(0xff4985f8)
                    )
                }
            }
        }

        FlatDoubleButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            rightText = "홈으로",
            leftText = "챌린지 알람 설정",
            onClickRight = {
                clickListener?.onClickHome()
            },
            onClickLeft = {
                clickListener?.onClickSetAlarm()
            }
        )
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
        "이용시간 인증"
    } else if (challengeData.is_verification_checkin == 1) {
        "이용권 인증"
    } else {
        "기타 인증"
    }
}