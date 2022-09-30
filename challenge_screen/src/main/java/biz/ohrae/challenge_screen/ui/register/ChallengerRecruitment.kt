package biz.ohrae.challenge_screen.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.card.ChallengeStartEndDateCard
import biz.ohrae.challenge.ui.components.checkBox.MyCheckBox
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.register.ChallengeOpenState


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ChallengerRecruitment(
    challengeData: ChallengeData? = ChallengeData.mock(),
    challengeOpenState: ChallengeOpenState? = ChallengeOpenState.mock(),
    clickListener: RegisterClickListener? = null,
    viewModel: ChallengeRegisterViewModel? = null
) {
    if (challengeOpenState == null) {
        return
    }

    val scrollState = rememberScrollState()
    var checked by remember { mutableStateOf(false) }
    var selectedDaysPosition by remember { mutableStateOf(challengeOpenState.authCycleList.size - 1) }

    Column(
        modifier = Modifier
            .background(DefaultWhite)
            .verticalScroll(scrollState)
            .padding(24.dp, 0.dp)
    ) {
        Text(
            text = "챌린저 모집",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 20.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "챌린지를 함께할 챌린저를 모집할 기간을 선택하세요",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff828282)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "챌린지 기간에는 챌린저 모집이 불가합니다",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff005bad)
        )
        Spacer(modifier = Modifier.height(28.dp))
        ChallengeStartEndDateCard(
            title = "챌린지 시작일",
            day = Utils.convertDate7(challengeData?.start_date.toString())
        )
        Spacer(modifier = Modifier.height(12.dp))
        ChallengeStartEndDateCard(
            title = "챌린지 종료일",
            day = Utils.convertDate7(challengeData?.end_date.toString())
        )
        Spacer(modifier = Modifier.height(12.dp))
        ChallengeStartEndDateCard(
            title = "모집 시작일",
            day = Utils.convertDate7(challengeData?.apply_start_date.toString())
        )
        Spacer(modifier = Modifier.height(12.dp))
        ChallengeStartEndDateCard(
            title = "모집 종료일",
            day = Utils.convertDate7(challengeData?.apply_end_date.toString())
        )
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
            selectedPosition = selectedDaysPosition,
            onSelectItem = {
                clickListener?.onClickRecruitDays(it)
            }
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
            style = myTypography.w500,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff828282)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "제한 나이 : 18세 미만",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff005bad)
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyCheckBox(
            checkBoxSize = 20.dp,
            label = "이용제한",
            labelStyle = myTypography.w700,
            onClick = {
                checked = !checked
                viewModel?.checkAdultOnly(checked)
            },
            onChecked = {
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
            text = "다음(${RegisterActivity.CHALLENGER}/4)",
            onClick = { clickListener?.onClickRecruitmentNext() }
        )
    }
}