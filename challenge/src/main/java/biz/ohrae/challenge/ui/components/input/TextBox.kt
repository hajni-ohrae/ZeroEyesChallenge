package biz.ohrae.challenge.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.*

@Preview(
    showBackground = true,
    backgroundColor = 0xfff8f8f8,
    widthDp = 360
)
@Composable
private fun TextBoxGallery() {
    Column(
        modifier = Modifier
            .background(DefaultWhite)
            .fillMaxWidth()
            .padding(24.dp, 0.dp)
    ) {
        TextBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f),
            placeholder = "예) 주 2회 자전거 타고 인증하기",
            maxLength = 60,
            singleLine = true,
            value = "",
            onValueChange = {}
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
            value = "",
            onValueChange = {}
        )
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TextBox(
    modifier: Modifier = Modifier,
    placeholder: String,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLength: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    singleLine: Boolean = false,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
) {
    Column(modifier = modifier) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            backgroundColor = Color(0xfff8f8f8),
            elevation = 0.dp,
            onClick = {
                focusRequester.requestFocus()
                keyboardController?.show()
            }
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 13.dp),
                verticalArrangement = if (singleLine) Arrangement.Center else Arrangement.Top
            ) {
                BasicTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = value,
                    onValueChange = {
                        if (it.length <= maxLength) {
                            onValueChange(it)
                        }
                    },
                    singleLine = singleLine,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    keyboardOptions = keyboardOptions,
                    decorationBox = @Composable { innerTextField ->
                        TextFieldDefaults.TextFieldDecorationBox(
                            innerTextField = innerTextField,
                            placeholder = {
                                Text(
                                    text = placeholder,
                                    color = Color(0xff6c6c6c),
                                    style = myTypography.default,
                                    fontSize = dpToSp(dp = 16.dp)
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                textColor = TextBlack,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            enabled = true,
                            singleLine = false,
                            interactionSource = interactionSource,
                            value = value,
                            visualTransformation = visualTransformation,
                            contentPadding = PaddingValues(0.dp),
                        )
                    }
                )
            }
        }
        if (maxLength != Int.MAX_VALUE) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${value.length}/${maxLength}",
                    style = myTypography.default,
                    color = Color(0xff828282),
                    fontSize = dpToSp(dp = 12.dp)
                )
            }
        }
    }
}