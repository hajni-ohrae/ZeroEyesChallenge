package biz.ohrae.challenge_screen.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.register.ChallengeOpenState
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.components.card.ChallengeStartEndDateCard
import biz.ohrae.challenge.ui.components.checkBox.CheckBox
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
fun ChallengerRecruitment(
    challengeOpenState: ChallengeOpenState = ChallengeOpenState.mock(),
    clickListener: RegisterClickListener? = null,
) {
    var checked by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .background(DefaultWhite)
        .padding(24.dp, 0.dp)) {
        Text(
            text = "챌린저 모집",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 20.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "챌린지를 함께할 챌린저를 모집할 기간을 선택하세요",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff828282)
        )
        Text(
            text = "챌린지 기간에는 챌린저 모집이 불가합니다",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff005bad)
        )
        Spacer(modifier = Modifier.height(28.dp))
        ChallengeStartEndDateCard("챌린지 시작일")
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "모집 기한",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "챌린지 시작전 최대 14일동안 모집 가능합니다",
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
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "미성년자 이용제한",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "성인이 아닌 회원에게는 챌린지가 노출되지 않습니다",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff828282)
        )
        Text(
            text = "제한 나이 : 18세 미만",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff005bad)
        )

        CheckBox(
            checkBoxSize = 20.dp,
            checkBoxSpacing = 4.dp,
            label = "이용제한",
            labelStyle = myTypography.w700,
            onClick = {
                checked = !checked
            },
            onCheckedChange = {
                checked = !checked
            },
            checked = checked,
        )

    }


    Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {

        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "다음",
            onClick = { clickListener?.onClickRecruitmentNext() }
        )
    }
}