package biz.ohrae.challenge.ui.components.checkBox

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    backgroundColor = 0xfff8f8f8,
    widthDp = 360
)
@Composable
private fun ChallengeCalendarCardGallery() {
    var checked by remember { mutableStateOf(false) }

    Column() {
        CheckBox(
            checkBoxSize = 20.dp,
            checkBoxSpacing = 4.dp,
            label = "이용제한",
            labelStyle = myTypography.w700,
            onClick = {
                checked = !checked
            },
            onCheckedChange = {
                checked = !checked
            },
            checked = checked,
        )
    }

}
@Composable
fun CheckBox(
    checkBoxSize: Dp,
    checkBoxSpacing: Dp,
    label: String,
    labelStyle: TextStyle,
    onClick: () -> Unit,
    onCheckedChange: ((Boolean) -> Unit)?,
    checked: Boolean = false
) {
    TextButton(
        colors = ButtonDefaults.buttonColors(
            contentColor = labelStyle.color,
            backgroundColor = Color.Transparent
        ),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                modifier = Modifier
                    .width(checkBoxSize)
                    .height(checkBoxSize),
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkmarkColor = DefaultWhite,
                    checkedColor = Color(0xff003865),
                    disabledColor = Color(0xffc7c7c7),
                    uncheckedColor = Color(0xffc7c7c7)
                )
            )
            Spacer(modifier = Modifier.width(checkBoxSpacing))
            Text(
                text = label,
                style = labelStyle,
                color =  Color(0xff6c6c6c)
            )
        }
    }
}
