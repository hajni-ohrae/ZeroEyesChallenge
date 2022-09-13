package biz.ohrae.challenge_screen.ui.participation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge.model.register.ChallengeOpenState
import biz.ohrae.challenge.ui.components.checkBox.CheckBox
import biz.ohrae.challenge.ui.components.input.LabeledTextField
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel2
import biz.ohrae.challenge.ui.theme.GrayColor4
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_screen.ui.register.ChallengeRegisterViewModel
import biz.ohrae.challenge_screen.ui.register.RegisterClickListener


@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ParticipationScreen(
    challengeData: ChallengeData = ChallengeData.mock(),
    clickListener: RegisterClickListener? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var checked by remember { mutableStateOf(false) }

    var participationAmount by remember { mutableStateOf("") }


    Column() {
        Card() {
            Column() {
                Text(text = "매일 6시간씩 한국사 공부")
                ChallengeDurationLabel2(dDay = "내일부터시작", week = "4주동안", numberOfTimes = "평일만")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "챌린지 기간")
                    Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "인증방식")
                    Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "참가인원")
                    Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "누적 참여금")
                    Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "평균 참여금")
                    Text(text = "${challengeData.start_date} ~ ${challengeData.end_date}")
                }
            }
        }
        Text(
            text = "습관에 돈을 걸고 의지를 유지하세요",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 20.dp),
            color = Color(0xffff5800),
        )
        LabeledTextField(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9f),
            label = "참여금",
            placeholder = "숫자만 입력",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            maxLength = 10,
            value = participationAmount,
            onValueChange = {
                participationAmount = it
            }
        )
        Text(text = "· 참여금이 높을수록 받는 리워즈도 많아져요\n· 최소 1천원 ~ 최대 50만원 까지 설정 가능해요")
        Text(text = "결제 금액")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "평균 참여금")
            CheckBox(
                checkBoxSize = 20.dp,
                checkBoxSpacing = 4.dp,
                label = "리워즈 전액 사용",
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
        LabeledTextField(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9f),
            label = "",
            placeholder = "숫자만 입력",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            maxLength = 10,
            value = participationAmount,
            onValueChange = {
                participationAmount = it
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "사용 가능한 리워즈")
            Text(text = "3,000원")
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), color = GrayColor4
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "최종 결제 금액")
            Text(text = "3,000원")
        }
        Text(text = "결제 수단")

    }
}