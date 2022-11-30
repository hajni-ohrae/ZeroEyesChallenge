package biz.ohrae.challenge_screen.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.calender.ChallengeCalendarCard
import biz.ohrae.challenge.ui.components.card.ChallengeStartEndDateCard
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.register.ChallengeOpenState
import biz.ohrae.challenge_screen.ui.register.RegisterActivity.Companion.OPEN


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ChallengeOpenScreen(
    challengeOpenState: ChallengeOpenState? = ChallengeOpenState.mock(),
    challengeData: ChallengeData? = ChallengeData.mock(),
    clickListener: RegisterClickListener? = null,
    viewModel: ChallengeRegisterViewModel? = null
) {
    if (challengeOpenState == null) {
        return
    }

    var selectedPeriod by remember { mutableStateOf(1) }
    Column(
        modifier = Modifier
            .background(DefaultWhite)
            .padding(24.dp, 0.dp)
    ) {
        Text(
            text = "챌린지 개설",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 20.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "인증은 24시간(1일) 이내에 한번만 가능합니다",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff005bad)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "챌린지 시작일",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "인증을 시작할 날짜를 선택하세요",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 14.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChallengeCalendarCard(
            Utils.convertDate7(challengeData?.start_date.toString()),
            R.drawable.calander,
            onClick = {
                clickListener?.onClickCalendar()
            })
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "인증 빈도",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "인증할 주기와 횟수를 선택하세요",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 14.dp),
            color = DefaultBlack
        )
        MyDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7.1f),
            label = "",
            list = challengeOpenState.authCycleList,
            onSelectItem = {
                selectedPeriod = it.value.toInt()
                clickListener?.onClickPeriod(it)
            }
        )

        MyDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7.1f),
            label = "",
            list = challengeOpenState.authFrequencyList,
            onSelectItem = {
                clickListener?.onClickPeriodType(it.value) }
        )

        if (challengeData?.is_verification_time == 1) {
            MyDropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7.1f),
                label = "",
                list = challengeOpenState.hoursOfUseList,
                onSelectItem = {
                    clickListener?.onClickHoursOfUse(it.value)
                }
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        ChallengeStartEndDateCard("챌린지 종료일", Utils.convertDate7(challengeData?.end_date.toString()),Color(0xffff5800))
    }
    Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "다음(${OPEN}/4)",
            onClick = { clickListener?.onClickOpenNext(weeks = selectedPeriod) }
        )
    }
}