package biz.ohrae.challenge.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.*

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun RadioButtonGallery() {
    Column(
    ) {
        RadioButton("Item", "사진인증", "즉석 촬영으로만 인증이 가능합니다")
    }
}


@Composable
fun RadioButton(
    label: String = "Item",
    buttonTitle: String,
    buttonContent: String
) {
    val selectedValue = remember { mutableStateOf("") }
//    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }
    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.RadioButton(
            selected = selectedValue.value == label,
            onClick = { selectedValue.value = label },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xff005bad),
                disabledColor = Color(0xffc7c7c7),
            ),
        )
        Column() {
            Text(
                text = buttonTitle,
                modifier = Modifier.fillMaxWidth(),
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = if (selectedValue.value == label) {
                    DefaultBlack
                } else {
                    Color(0xff6c6c6c)
                },
            )
            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = buttonContent,
                modifier = Modifier.fillMaxWidth(),
                style = myTypography.w700,
                fontSize = dpToSp(dp = 12.dp),
                color = Color(0xff6c6c6c)
            )
        }
    }


}