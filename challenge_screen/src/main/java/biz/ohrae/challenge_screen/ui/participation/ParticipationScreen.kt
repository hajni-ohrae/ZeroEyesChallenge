package biz.ohrae.challenge_screen.ui.participation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.checkBox.MyCheckBox
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.components.input.TextBox
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel2
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.GrayColor4
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeVerificationPeriodMap
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_repo.util.prefs.Utils.numberToString
import java.lang.Math.min
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ParticipationScreen(
    challengeData: ChallengeData = ChallengeData.mock(),
    clickListener: ParticipationClickListener? = null,
) {

    val scrollState = rememberScrollState()

    val list = listOf(
        DropDownItem(label = "신용카드", value = "card"),
    )
    var participationAmount by remember { mutableStateOf("") }
    var rewards by remember { mutableStateOf("") }
    val availableRewards by remember {
        mutableStateOf(challengeData.user?.rewards_amount ?: 0)
    }
    val isFree by remember {
        mutableStateOf(challengeData.min_deposit_amount <= 0)
    }
    var paidAmount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(DefaultWhite)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp, 0.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ParticipationDetailCard(challengeData = challengeData)
            Spacer(modifier = Modifier.height(24.dp))
            InputParticipationAmount(
                isFree = isFree,
                availableRewards = availableRewards,
                participationAmount = participationAmount,
                onParticipationAmountChange = { text, checked ->
                    val price = numberToString(text)
                    participationAmount = price
                    if (checked) {
                        val amount = try {
                            participationAmount.replace(",", "").toInt()
                        } catch (e: Exception) {
                            0
                        }
                        val ownRewards = challengeData.user?.rewards_amount ?: 0
                        val min = min(amount, ownRewards)
                        rewards = numberToString(text, min)
                        paidAmount = calculatePaidAmount(participationAmount, rewards)
                    } else {
                        paidAmount = calculatePaidAmount(participationAmount, rewards)
                    }
                },
                rewards = rewards,
                onParticipationRewardChange = {
                    val amount = try {
                        participationAmount.replace(",", "").toInt()
                    } catch (e: Exception) {
                        0
                    }
                    val ownRewards = challengeData.user?.rewards_amount ?: 0
                    val min = min(amount, ownRewards)
                    rewards = numberToString(it, min)
                    paidAmount = calculatePaidAmount(participationAmount, rewards)
                },
                onCheckUseAllRewards = {
                    val amount = try {
                        participationAmount.replace(",", "").toInt()
                    } catch (e: Exception) {
                        0
                    }
                    var ownRewards = challengeData.user?.rewards_amount ?: 0
                    if (ownRewards >= amount) {
                        ownRewards = amount
                    }

                    if (!it) {
                        ownRewards = 0
                    }

                    rewards = Utils.numberFormat(ownRewards)
                    paidAmount = calculatePaidAmount(participationAmount, rewards)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Text(text = "최종 결제 금액", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
                Text(
                    text = "${Utils.numberFormat(paidAmount)}원",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 18.dp),
                    color = Color(0xff4985f8)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "결제 수단")
            MyDropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7.1f),
                label = "", list = list
            )
            Spacer(modifier = Modifier.height(185.dp))
        }
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = if (isFree) "참여하기" else "결제하기",
            onClick = {
                val depositAmount = if (participationAmount.isEmpty()) {
                    0
                } else {
                    participationAmount.replace(",", "").toInt()
                }
                val rewardAmount = if (rewards.isEmpty()) {
                    0
                } else {
                    rewards.replace(",", "").toInt()
                }

                clickListener?.onClickPayment(
                    paidAmount = paidAmount,
                    rewardAmount = rewardAmount,
                    depositAmount = depositAmount
                )
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun InputParticipationAmount(
    isFree: Boolean,
    availableRewards: Int,
    participationAmount: String,
    onParticipationAmountChange: (text: String, checked: Boolean) -> Unit,
    rewards: String,
    onParticipationRewardChange: (text: String) -> Unit,
    onCheckUseAllRewards: (checked: Boolean) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var checked by remember { mutableStateOf(false) }

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
            .aspectRatio(7.1f),
        placeholder = if (isFree) "무료챌린지 입니다." else "숫자만 입력",
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        singleLine = true,
        enabled = !isFree,
        value = participationAmount,
        onValueChange = {
            onParticipationAmountChange(it, checked)
        },
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
        Text(
            text = "리워즈 사용",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 14.dp)
        )
        MyCheckBox(
            checkBoxSize = 20.dp,
            label = "리워즈 전액 사용",
            labelStyle = myTypography.w700,
            onClick = {
                checked = !checked
                onCheckUseAllRewards(checked)
            },
            onChecked = {
                checked = !checked
                onCheckUseAllRewards(checked)
            },
            checked = checked,
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    TextBox(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(7.1f),
        placeholder = if (availableRewards > 0) "숫자만 입력" else "사용가능한 리워즈가 없습니다.",
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        singleLine = true,
        enabled = availableRewards > 0 && !checked,
        value = rewards,
        onValueChange = {
            onParticipationRewardChange(it)
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
        Text(
            text = "${numberToString(availableRewards.toString())}원",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 14.dp)
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp), color = GrayColor4
    )
}

@Composable
fun ParticipationDetailCard(challengeData: ChallengeData) {
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
                    text = "${Utils.numberFormat(challengeData.summary?.total_user_cnt)}명",
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
                    text = "총 ${Utils.numberFormat(challengeData.summary?.total_amount)}원",
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
                    text = "${Utils.numberFormat(challengeData.summary?.average_amount)}원",
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
        numberOfTimes = dayType.toString()
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


private fun calculatePaidAmount(priceText: String, rewardsText: String): Int {
    val price = priceText.replace("[^\\d]".toRegex(), "")
    val rewards = rewardsText.replace("[^\\d]".toRegex(), "")

    val priceNumber = if (price.isEmpty()) {
        0
    } else {
        price.toInt()
    }

    val rewardsNumber = if (rewards.isEmpty()) {
        0
    } else {
        rewards.toInt()
    }

    var result = 0
    if (priceNumber - rewardsNumber > 0) {
        result = priceNumber - rewardsNumber
    }
    return result
}
