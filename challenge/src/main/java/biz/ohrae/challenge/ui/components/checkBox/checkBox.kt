package biz.ohrae.challenge.ui.components.checkBox

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R

@Preview(
    showBackground = true,
    backgroundColor = 0xfff8f8f8,
    widthDp = 360
)
@Composable
private fun ChallengeCalendarCardGallery() {
    var checked by remember { mutableStateOf(false) }

    Column() {
        MyCheckBox(
            checkBoxSize = 20.dp,
            label = "이용제한",
            labelStyle = myTypography.w700,
            onClick = {
                checked = !checked
            },
            checked = checked,
        )
    }

}

@Composable
fun MyCheckBox(
    checkBoxSize: Dp = 20.dp,
    label: String,
    labelStyle: TextStyle,
    onClick: () -> Unit = {},
    onChecked: () -> Unit = {},
    checked: Boolean = false
) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier
                .width(checkBoxSize)
                .height(checkBoxSize)
                .aspectRatio(1f)
                .clickable (onClick = onChecked ),
            shape = RoundedCornerShape(4.dp),
            border = if (checked) BorderStroke(0.dp, Color(0xffc7c7c7)) else BorderStroke(1.dp, Color(0xffc7c7c7)),
            color = if (checked) Color(0xff005bad) else DefaultWhite
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(id = R.drawable.check_empty),
                    contentDescription = "check_empty",
                    tint = DefaultWhite
                )
            }
        }

        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = labelStyle, color = Color(0xff6c6c6c)
        )
    }
}

