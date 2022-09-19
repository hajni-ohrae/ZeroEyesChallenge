package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown
import biz.ohrae.challenge.ui.components.input.LabeledTextField
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun PhoneAuthScreen() {
    val list = listOf(
        DropDownItem(label = "SK", value = "SK"),
        DropDownItem(label = "KT", value = "KT"),
        DropDownItem(label = "LGU+", value = "LG"),
        DropDownItem(label = "알뜰폰", value = "frugal"),
    )
    Column() {
        Column(modifier = Modifier.padding(24.dp, 0.dp)) {
            Text(text = "휴대폰 본인인증", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
            Row() {
                MyDropDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(7.1f),
                    label = "휴대폰번호",
                    list = list
                )
                LabeledTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(9f),
                    label = "",
                    placeholder = "휴대폰번호 11자리 입력",
                    maxLength = 11,
                    value = "",
                    onValueChange = {}
                )

            }
        }

        Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
            FlatBottomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                text = "확인",
                onClick = { },
//                enabled =
            )
        }
    }
}