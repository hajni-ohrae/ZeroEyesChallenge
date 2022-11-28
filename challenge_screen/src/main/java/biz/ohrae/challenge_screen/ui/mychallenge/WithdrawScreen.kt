package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.banner.FlatBanner
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.components.input.LabeledTextField
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.util.prefs.Utils

@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun WithdrawScreen(
    rewardsAmount: Int = 0,
    clickListener: MyChallengeClickListener? = null
) {
    Column {
        val keyboardController = LocalSoftwareKeyboardController.current
        var amount by remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(24.dp,0.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "출금 신청", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "5,000원 이상부터 출금 가능합니다",
                style = myTypography.w700,
                fontSize = dpToSp(dp = 12.dp),
                color = Color(0xff828282)
            )
            Spacer(modifier = Modifier.height(20.dp))
            FlatBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6.37f),
                backgroundColor = Color(0xfff3f8ff),
                title = "이체 가능한 리워즈",
                titleColor = TextBlack,
                content = "${Utils.numberFormat(rewardsAmount)}원",
                contentColor = Color(0xff005bad)
            )
            Spacer(modifier = Modifier.height(20.dp))

            LabeledTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(9f),
                label = "금액",
                placeholder = "이체할 금액을 숫자만 입력해주세요",
                maxLength = 20,
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = Utils.numberToString(amount, rewardsAmount),
                onValueChange = {
                    amount = it
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "출금신청",
            onClick = {
                clickListener?.onClickApplyWithdrawDetail()
            }
        )
    }
}

@Composable
fun NoAccountScreen(){

    val list = listOf(
        DropDownItem(label = "우리은행", value = "우리은행"),
        DropDownItem(label = "국민은행", value = "국민은행"),
        DropDownItem(label = "농협", value = "농협"),
        DropDownItem(label = "토스뱅크", value = "토스뱅크"),
        DropDownItem(label = "카카오뱅크", value = "카카오뱅크"),
    )

    Column(modifier = Modifier.padding(24.dp,0.dp)) {
        Text(text = "출금 계좌 인증", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
        MyDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7.1f),
            label = "은행명",
            list = list
        )
        LabeledTextField(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9f),
            label = "계좌번호",
            placeholder = "계좌번호 입력",
            maxLength = 20,
            value = "",
            onValueChange = {}
        )

        Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
            FlatBottomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                text = "인증",
                onClick = { }
            )
        }
    }
}