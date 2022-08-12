package biz.ohrae.challenge.ui.components.button

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun modifierGallery() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DialogButton(
            modifier = Modifier.fillMaxWidth().aspectRatio(6.4f),
            text = "동의하고 개설",
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        DialogButton(
            modifier = Modifier.fillMaxWidth().aspectRatio(6.4f),
            text = "동의하고 개설",
            enabled = false,
            onClick = {}
        )
    }
}

@Composable
fun DialogButton(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.bold,
    backgroundColor: Color = Color(0xff003865),
    disabledColor: Color = Color(0xffc7c7c7),
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (enabled) backgroundColor else disabledColor,
            contentColor = DefaultWhite
        ),
        shape = RectangleShape,
        onClick = {
            if (enabled) {
                onClick()
            }
        }
    ) {
        Text(
            text = text,
            style = textStyle,
            fontSize = dpToSp(dp = 17.dp)
        )
    }
}