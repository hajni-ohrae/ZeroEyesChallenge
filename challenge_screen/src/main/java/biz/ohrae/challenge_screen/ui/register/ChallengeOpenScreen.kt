package biz.ohrae.challenge_screen.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.register.ChallengeOpenState
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ChallengeOpenScreen(
    challengeOpenState: ChallengeOpenState = ChallengeOpenState.mock()
) {
    Column(modifier = Modifier.background(DefaultWhite)) {
        Text(
            text = "챌린지 개설",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 20.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "인증은 24시간(1일) 이내에 한번만 가능합니다",
            style = myTypography.w700,
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
            style = myTypography.w700,
            fontSize = dpToSp(dp = 14.dp),
            color = DefaultBlack
        )
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
            style = myTypography.w700,
            fontSize = dpToSp(dp = 14.dp),
            color = DefaultBlack
        )
        MyDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7.1f),
            label = "",
            list = challengeOpenState.authCycleList
        )
        MyDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7.1f),
            label = "",
            list = challengeOpenState.authFrequencyList
        )
        MyDropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7.1f),
        label = "",
        list = challengeOpenState.authFrequencyList
        )
    }

}