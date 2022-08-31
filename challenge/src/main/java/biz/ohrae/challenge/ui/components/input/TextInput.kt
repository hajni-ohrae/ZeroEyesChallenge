package biz.ohrae.challenge.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.*

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun TextFieldGallery() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp).background(DefaultWhite)
    ) {
        LabeledTextField(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9f),
            label = "계좌번호",
            placeholder = "1006-123-142-67",
            maxLength = 20,
            value = "",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        LabeledTextField(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9f),
            label = "인증번호",
            placeholder = "숫자 3자리 입력",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLength = 3,
            value = "",
            onValueChange = {}
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LabeledTextField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLength: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = myTypography.default,
            fontSize = dpToSp(dp = 10.dp),
            color = Color(0xff6c6c6c)
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            modifier = modifier,
            value = value,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            singleLine = true,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    innerTextField = innerTextField,
                    placeholder = {
                        Text(
                            text = placeholder,
                            color = Color(0xff6c6c6c),
                            style = myTypography.default
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
                    singleLine = true,
                    interactionSource = interactionSource,
                    value = value,
                    visualTransformation = VisualTransformation.None,
                    contentPadding = PaddingValues(0.dp),
                )
            }
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffc7c7c7))
        )
    }
}
