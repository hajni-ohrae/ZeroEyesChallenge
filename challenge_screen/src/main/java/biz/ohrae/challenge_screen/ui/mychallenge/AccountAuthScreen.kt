import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.components.input.LabeledTextField
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_screen.ui.mychallenge.AccountAuthScreenState
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeClickListener

@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun AccountAuthScreen(
    accountScreenState: AccountAuthScreenState = AccountAuthScreenState.mock(),
    clickListener: MyChallengeClickListener? = null
){
    val list = listOf(
        DropDownItem(label = "우리은행", value = "우리은행"),
        DropDownItem(label = "국민은행", value = "국민은행"),
        DropDownItem(label = "농협", value = "농협"),
        DropDownItem(label = "토스뱅크", value = "토스뱅크"),
        DropDownItem(label = "카카오뱅크", value = "카카오뱅크"),
    )
    var buttonEnabled by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(24.dp, 0.dp)) {
            var accountNumber by remember { mutableStateOf("") }
            var authNumber by remember { mutableStateOf("") }
            val keyboardController = LocalSoftwareKeyboardController.current

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "출금 계좌 인증", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
            Spacer(modifier = Modifier.height(28.dp))
            MyDropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7.1f),
                label = "은행명",
                list = list
            )
            Spacer(modifier = Modifier.height(28.dp))
            LabeledTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(9f),
                label = "계좌번호",
                placeholder = "계좌번호 입력",
                maxLength = 30,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                value = accountNumber,
                onValueChange = {
                    accountNumber = it
                    if (it.isNotEmpty()) {
                        buttonEnabled = true
                    }
                }
            )
            Spacer(modifier = Modifier.height(28.dp))
            if (accountScreenState.isAuthed) {
                ExampleCard()
                Spacer(modifier = Modifier.height(28.dp))
                LabeledTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(9f),
                    label = "인증 번호 입력",
                    placeholder = "숫자 3자리 입력",
                    maxLength = 3,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    value = authNumber,
                    onValueChange = {
                        authNumber = it
                        if (it.isNotEmpty() && authNumber.isNotEmpty()) {
                            buttonEnabled = true
                        }
                    }
                )
            }
        }
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = accountScreenState.buttonName,
            enabled = buttonEnabled,
            onClick = {
                if (buttonEnabled) {
                    clickListener?.onClickAccountAuth(false)
                    buttonEnabled = false
                }
            }
        )
    }
}

@Composable
private fun ExampleCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3.08f),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(0xfff8f8f8),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "입금자명 옆 숫자 3자리를 입력해주세요",
                color = Color(0xff005bad),
                fontSize = dpToSp(dp = 16.dp),
                style = myTypography.extraBold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "예) 감자왕154",
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                    style = myTypography.extraBold
                )
                Text(
                    text = "1원",
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
    }
}