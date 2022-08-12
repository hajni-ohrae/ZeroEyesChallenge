package biz.ohrae.challenge.ui.components.label

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ProgressLabelGallery() {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            ProgressLabel(
                text = "진행중",
                type = "ongoing",
            )
            Spacer(modifier = Modifier.width(10.dp))
            ProgressLabel(
                text = "모집중",
                type = "recruiting"
            )
            Spacer(modifier = Modifier.width(10.dp))
            ProgressLabel(
                text = "완료",
                type = "completion"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        ProgressLabel(
            text = "100% 환급 + 리워즈",
            type = "rewards"
        )
    }
}

@Composable
fun ProgressLabel(
    modifier: Modifier = Modifier,
    text: String,
    type: String
) {
    val backgroundColor = when (type) {
        "ongoing" -> Color(0xfff3f8ff)
        "recruiting" -> Color(0xffebfaf1)
        "completion" -> Color(0xffdedede)
        "rewards" -> Color(0xfff3f8ff)
        else -> {
            Color(0xfff3f8ff)
        }
    }

    val textColor = when (type) {
        "ongoing" -> Color(0xff4985f8)
        "recruiting" -> Color(0xff219653)
        "completion" -> Color(0xff6c6c6c)
        "rewards" -> Color(0xff4985f8)
        else -> {
            Color(0xff4985f8)
        }
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            style = myTypography.bold,
            color = textColor,
            fontSize = dpToSp(dp = 12.dp)
        )
    }
}