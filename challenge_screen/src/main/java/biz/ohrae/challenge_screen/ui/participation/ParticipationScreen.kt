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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.checkBox.MyCheckBox
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.components.input.TextBox
import biz.ohrae.challenge.ui.components.input.TextBox2
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
        DropDownItem(label = "????????????", value = "card"),
    )
    var participationAmount by remember { mutableStateOf(TextFieldValue("")) }
    var rewards by remember { mutableStateOf(TextFieldValue("")) }
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
            .background(DefaultWhite)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp, 0.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ParticipationDetailCard(challengeData = challengeData)
            Spacer(modifier = Modifier.height(24.dp))
            if (isFree) {
                FreeChallengeText()
            } else {
                InputParticipationAmount2(
                    isFree = isFree,
                    availableRewards = availableRewards,
                    participationAmount = participationAmount,
                    onParticipationAmountChange = { text, checked ->
                        val price = numberToString(text.text)
                        participationAmount = TextFieldValue(
                            text = price,
                            selection = TextRange(price.length, price.length)
                        )
                        if (checked) {
                            val amount = try {
                                participationAmount.text.replace(",", "").toInt()
                            } catch (e: Exception) {
                                0
                            }
                            val ownRewards = challengeData.user?.rewards_amount ?: 0
                            val min = min(amount, ownRewards)
                            rewards = TextFieldValue(numberToString(text.text, min))
                            paidAmount = calculatePaidAmount(participationAmount.text, rewards.text)
                        } else {
                            val amount = try {
                                participationAmount.text.replace(",", "").toInt()
                            } catch (e: Exception) {
                                0
                            }
                            val rewardsAmount = try {
                                rewards.text.replace(",", "").toInt()
                            } catch (e: Exception) {
                                0
                            }
                            val min = min(amount, rewardsAmount)
                            rewards = TextFieldValue(numberToString(text.text, min))
                            paidAmount = calculatePaidAmount(participationAmount.text, rewards.text)

                        }
                    },
                    rewards = rewards,
                    onParticipationRewardChange = {
                        val amount = try {
                            participationAmount.text.replace(",", "").toInt()
                        } catch (e: Exception) {
                            0
                        }
                        val ownRewards = challengeData.user?.rewards_amount ?: 0
                        val min = min(amount, ownRewards)
                        val price = numberToString(it.text)
                        rewards = TextFieldValue(
                            text = numberToString(price, min),
                            selection = TextRange(price.length, price.length)
                        )
                        paidAmount = calculatePaidAmount(participationAmount.text, rewards.text)
                    },
                    onCheckUseAllRewards = {
                        val amount = try {
                            participationAmount.text.replace(",", "").toInt()
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

                        val rewardsValue = Utils.numberFormat(ownRewards)
                        rewards = TextFieldValue(
                            text = rewardsValue,
                            selection = TextRange(rewardsValue.length, rewardsValue.length)
                        )
                        paidAmount = calculatePaidAmount(participationAmount.text, rewards.text)
                    }
                )
            }
            if (!isFree) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(text = "?????? ?????? ??????", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
                    Text(
                        text = "${Utils.numberFormat(paidAmount)}???",
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 18.dp),
                        color = Color(0xff4985f8)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "????????????")
                MyDropDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(7.1f),
                    label = "", list = list
                )
                Spacer(modifier = Modifier.height(141.dp))
            }
        }
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            text = if (isFree) "????????????" else "????????????",
            onClick = {
                val depositAmount = if (participationAmount.text.isEmpty()) {
                    0
                } else {
                    participationAmount.text.replace(",", "").toInt()
                }
                val rewardAmount = if (rewards.text.isEmpty()) {
                    0
                } else {
                    rewards.text.replace(",", "").toInt()
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
        text = "????????? ???????????? ?????? ????????? ???????????????",
        style = myTypography.w500,
        fontSize = dpToSp(dp = 20.dp),
        color = Color(0xffff5800),
    )
    Spacer(modifier = Modifier.height(24.dp))
    Text(text = "?????????", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
    Spacer(modifier = Modifier.height(8.dp))
    TextBox(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(7.1f),
        placeholder = if (isFree) "??????????????? ?????????." else "????????? ??????",
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
        text = "?? ???????????? ???????????? ?????? ???????????? ????????????\n?? ?????? 1?????? ~ ?????? 50?????? ?????? ?????? ????????????",
        style = myTypography.w500,
        lineHeight = dpToSp(dp = 19.6.dp),
        fontSize = dpToSp(dp = 14.dp),
    )
    Spacer(modifier = Modifier.height(14.dp))

    Text(text = "?????? ??????", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Text(
            text = "????????? ??????",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 14.dp)
        )
        MyCheckBox(
            checkBoxSize = 20.dp,
            label = "????????? ?????? ??????",
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
        placeholder = if (availableRewards > 0) "????????? ??????" else "??????????????? ???????????? ????????????.",
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
            text = "?????? ????????? ?????????",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 14.dp),
            color = Color(0xff6c6c6c)
        )
        Text(
            text = "${numberToString(availableRewards.toString())}???",
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun InputParticipationAmount2(
    isFree: Boolean,
    availableRewards: Int,
    participationAmount: TextFieldValue,
    onParticipationAmountChange: (text: TextFieldValue, checked: Boolean) -> Unit,
    rewards: TextFieldValue,
    onParticipationRewardChange: (text: TextFieldValue) -> Unit,
    onCheckUseAllRewards: (checked: Boolean) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var checked by remember { mutableStateOf(false) }

    Text(
        text = "????????? ???????????? ?????? ????????? ???????????????",
        style = myTypography.w500,
        fontSize = dpToSp(dp = 20.dp),
        color = Color(0xffff5800),
    )
    Spacer(modifier = Modifier.height(24.dp))
    Text(text = "?????????", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
    Spacer(modifier = Modifier.height(8.dp))
    TextBox2(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(7.1f),
        placeholder = if (isFree) "??????????????? ?????????." else "????????? ??????",
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
        text = "?? ???????????? ???????????? ?????? ???????????? ????????????\n?? ?????? 1?????? ~ ?????? 50?????? ?????? ?????? ????????????",
        style = myTypography.w500,
        lineHeight = dpToSp(dp = 19.6.dp),
        fontSize = dpToSp(dp = 14.dp),
    )
    Spacer(modifier = Modifier.height(14.dp))

    Text(text = "?????? ??????", style = myTypography.bold, fontSize = dpToSp(dp = 16.dp))
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        Text(
            text = "????????? ??????",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 14.dp)
        )
        MyCheckBox(
            checkBoxSize = 20.dp,
            label = "????????? ?????? ??????",
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
    TextBox2(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(7.1f),
        placeholder = if (availableRewards > 0) "????????? ??????" else "??????????????? ???????????? ????????????.",
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
            text = "?????? ????????? ?????????",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 14.dp),
            color = Color(0xff6c6c6c)
        )
        Text(
            text = "${numberToString(availableRewards.toString())}???",
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
                    text = "????????? ??????",
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
                    text = "????????????",
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
                    text = "????????????",
                    style = myTypography.w500,
                    fontSize = dpToSp(dp = 13.dp),
                    color = Color(0xff4f4f4f)
                )
                Text(
                    text = "${Utils.numberFormat(challengeData.summary?.total_user_cnt)}???",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 13.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (challengeData.min_deposit_amount > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "?????? ?????????",
                        style = myTypography.w500,
                        fontSize = dpToSp(dp = 13.dp),
                        color = Color(0xff4f4f4f)
                    )
                    Text(
                        text = "${Utils.numberFormat(challengeData.summary?.total_amount)}???",
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
                        text = "?????? ?????????",
                        style = myTypography.w500,
                        fontSize = dpToSp(dp = 13.dp),
                        color = Color(0xff4f4f4f)
                    )
                    Text(
                        text = "${Utils.numberFormat(challengeData.summary?.average_amount)}???",
                        style = myTypography.w500,
                        fontSize = dpToSp(dp = 13.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun FreeChallengeText(){
    Column() {
        Text(
            text = "?????? ?????????",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "?? ???????????? ?????? ?????? ?????? ??????????????????",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 14.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "?? ???????????? ?????? ????????? ?????? ???????????? ???????????????",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 14.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), color = GrayColor4
        )

    }

}
@Composable
private fun DurationLabel(challengeData: ChallengeData) {
    val day by remember { mutableStateOf(Utils.getRemainTimeDays(challengeData.start_date.toString())) }
    val dayType by remember { mutableStateOf(challengeVerificationPeriodMap[challengeData.verification_period_type]) }

    ChallengeDurationLabel2(
        dDay = day,
        week = "${challengeData.period}?????????",
        numberOfTimes = if (dayType.isNullOrEmpty()) "???${challengeData.per_week}???" else dayType.toString()
    )
}

private fun Long?.formatWithComma(): String =
    NumberFormat.getNumberInstance(Locale.US).format(this ?: 0)

private fun getAuthText(challengeData: ChallengeData): String {
    return if (challengeData.is_verification_photo == 1) {
        "?????? ??????"
    } else if (challengeData.is_verification_time == 1) {
        "???????????? ??????(?????? ??????)"
    } else if (challengeData.is_verification_checkin == 1) {
        "?????? ??????(?????? ??????)"
    } else {
        "?????? ??????"
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
