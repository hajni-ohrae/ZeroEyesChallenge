package biz.ohrae.challenge.ui.components.button

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
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
private fun ButtonGallery() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        FlatTextButton(
            modifier = Modifier.fillMaxWidth().aspectRatio(6.5f),
            text = "확인",
            backgroundColor = appColor.AlertSuccessColor
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlatTextButton(
            modifier = Modifier.fillMaxWidth().aspectRatio(6.5f),
            text = "다음",
            backgroundColor = appColor.AlertSuccessColor,
            enabled = false
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlatTextButton(
            modifier = Modifier.fillMaxWidth().aspectRatio(6.5f),
            text = "취소",
            backgroundColor = appColor.AlertWarningColor,
        )
    }
}

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
fun FlatTextButton(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.w700,
    backgroundColor: Color = Color(0xffeb712d),
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (enabled) backgroundColor else GrayColor7,
            contentColor = DefaultWhite
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            if (enabled) {
                onClick()
            }
        }
    ) {
        Text(
            text = text,
            style = textStyle,
            fontSize = dpToSp(dp = 14.dp)
        )
    }
}