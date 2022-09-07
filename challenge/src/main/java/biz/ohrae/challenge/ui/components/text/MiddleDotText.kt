package biz.ohrae.challenge.ui.components.text

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.dpToSp

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun MiddleDotTextGallery() {
    Column(modifier = Modifier.fillMaxWidth()) {
        MiddleDotText(
            text = "4주 동안 매일, 이용권 사용 내역이 이용시간으로 자동 인증됩니다.",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        MiddleDotText(
            text = "인증 가능한 요일은 월,화,수,목,금,토,일 입니다",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        MiddleDotText(
            text = "이용시간은 이용권으로 입실한 시점부터 퇴실까지의 시간이 자동 누적됩니다 (외출시간 포함)",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        MiddleDotText(
            text = "인증샷 피드에 이용권 사용 시간이 공개됩니다",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
    }
}

@Composable
fun MiddleDotText(
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
) {
    Row {
        Text(
            text = "· ",
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            lineHeight = lineHeight,
        )
        Text(
            text = text,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            lineHeight = lineHeight,
        )
    }
}

