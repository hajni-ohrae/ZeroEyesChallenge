package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.components.input.TextBox
import biz.ohrae.challenge.ui.theme.DefaultWhite

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengeDetailAuthWriteScreen(
    clickListener: ChallengeDetailClickListener? = null,
) {
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultWhite)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextBox(
                modifier = Modifier.fillMaxSize(),
                placeholder = "내용을입력하세요",
                maxLength = 1000,
                singleLine = false,
                value = content,
                onValueChange = {
                    content = it
                },
            )
        }
        FlatDoubleButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            leftText = "재촬영",
            rightText = "완료",
            onClickLeft = {
                clickListener?.onClickReTakePhoto()
            },
            onClickRight = {
                clickListener?.onDone(content)
            }
        )
    }
}