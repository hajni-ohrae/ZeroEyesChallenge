package biz.ohrae.challenge_screen.ui.participation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.checkBox.CheckBox
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.components.input.TextBox
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel2
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.GrayColor4
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeVerificationPeriodMap
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.ui.register.RegisterClickListener

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

//    val keyboardController = LocalSoftwareKeyboardController.current
    var checked by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val list = listOf(
        DropDownItem(label = "신용카드", value = "card"),
    )
    var participationAmount by remember { mutableStateOf("") }
    var rewards by remember { mutableStateOf("") }
    var availableRewards  = 3000
    val startDate by remember { mutableStateOf(Utils.convertDate6(challengeData.start_date.toString())) }
    val endDate by remember { mutableStateOf(Utils.convertDate6(challengeData.end_date.toString())) }
    val authType by remember { mutableStateOf(getAuthText(challengeData)) }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .background(DefaultWhite)) {
        Column(
            modifier = Modifier
                .padding(24.dp, 0.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
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
                        verticalAlignment = CenterVertically
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
                        verticalAlignment = CenterVertically
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            text = "참가인원",
                            style = myTypography.w500,
                            fontSize = dpToSp(dp = 13.dp),
                            color = Color(0xff4f4f4f)
                        )
                        Text(
                            text = "0명",
                            style = myTypography.bold,
                            fontSize = dpToSp(dp = 13.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            text = "누적 참여금",
                            style = myTypography.w500,
                            fontSize = dpToSp(dp = 13.dp),
                            color = Color(0xff4f4f4f)
                        )
                        Text(
                            text = "총 1,000원",
                            style = myTypography.bold,
                            fontSize = dpToSp(dp = 13.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            text = "평균 참여금",
                            style = myTypography.w500,
                            fontSize = dpToSp(dp = 13.dp),
                            color = Color(0xff4f4f4f)
                        )
                        Text(
                            text = "100원",
                            style = myTypography.w500,
                            fontSize = dpToSp(dp = 13.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "습관에 돈을 걸고 의지를 유지하세요",
                style = myTypography.w500,
                fontSize = dpToSp(dp = 20.dp),
                color = Color(0xffff5800),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "참여금", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f),
                placeholder = "숫자만 입력",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                value = participationAmount,
                onValueChange = {
                    participationAmount = it
                }
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "· 참여금이 높을수록 받는 리워즈도 많아져요\n· 최소 1천원 ~ 최대 50만원 까지 설정 가능해요",
                style = myTypography.w500,
                lineHeight = dpToSp(dp = 19.6.dp),
                fontSize = dpToSp(dp = 14.dp),
            )
            Spacer(modifier = Modifier.height(14.dp))

            Text(text = "결제 금액", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(text = "리워즈 사용", style = myTypography.bold, fontSize = dpToSp(dp = 14.dp))
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
            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f),
                placeholder = "숫자만 입력",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                value = rewards,
                onValueChange = {
                    rewards = it
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "사용 가능한 리워즈",
                    style = myTypography.w500,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff6c6c6c)
                )
                Text(text = "${availableRewards}원", style = myTypography.bold, fontSize = dpToSp(dp = 14.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp), color = GrayColor4
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(text = "최종 결제 금액", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
                Text(
                    text = "3,000원",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 18.dp),
                    color = Color(0xff4985f8)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "결제 수단")
            MyDropDown(
                modifier = Modifier.fillMaxWidth().aspectRatio(7.1f),
                label = "", list = list
            )
            Spacer(modifier = Modifier.height(185.dp))
        }
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "결제하기",
            onClick = { }
        )
    }
}

@Composable
private fun DurationLabel(challengeData: ChallengeData) {
    val day by remember { mutableStateOf(Utils.getRemainTimeDays(challengeData.start_date.toString())) }
    val dayType by remember { mutableStateOf(challengeVerificationPeriodMap[challengeData.verification_period_type]) }

    ChallengeDurationLabel2(dDay = day, week = "${challengeData.period}주동안", numberOfTimes = dayType.toString())
}


private fun getAuthText(challengeData: ChallengeData): String {
    return if (challengeData.is_verification_photo == 1) {
        "사진 인증"
    } else if(challengeData.is_verification_time == 1) {
        "이용시간 인증"
    } else if(challengeData.is_verification_checkin == 1) {
        "이용권 인증"
    } else {
        "기타 인증"
    }
}