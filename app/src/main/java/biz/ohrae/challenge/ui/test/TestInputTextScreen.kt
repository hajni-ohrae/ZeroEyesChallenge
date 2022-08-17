package biz.ohrae.challenge.ui.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.input.LabeledTextField
import biz.ohrae.challenge.ui.components.input.TextBox

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TestInputTextScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    var bankNumber by remember { mutableStateOf("") }
    var authNumber by remember { mutableStateOf("") }
    var longText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp)
            .verticalScroll(scrollState, reverseScrolling = true)
            .navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        LabeledTextField(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9f),
            label = "계좌번호",
            placeholder = "1006-123-142-67",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            maxLength = 20,
            value = bankNumber,
            onValueChange = {
                bankNumber = it
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        LabeledTextField(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9f),
            label = "인증번호",
            placeholder = "숫자 3자리 입력",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            maxLength = 3,
            value = authNumber,
            onValueChange = {
                authNumber = it
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.122f),
            placeholder = "예) 공부하는 책과 함께 손이 나온 사진 찍기\n" +
                    " \n" +
                    "- 만화책은 안됩니다\n" +
                    "- 손은 어떤 제스처든 모두 가능해요",
            maxLength = 1000,
            value = longText,
            onValueChange = {
                longText = it
            }
        )
    }
}