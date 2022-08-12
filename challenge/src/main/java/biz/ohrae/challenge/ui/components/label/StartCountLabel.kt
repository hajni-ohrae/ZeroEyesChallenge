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
    Row(modifier = Modifier.fillMaxWidth()) {
        StartCountLabel(
            modifier = Modifier,
            dDay = "무료", week = "4주동안", numberOfTimes = "매일"
        )
        Spacer(modifier = Modifier.width(10.dp))
        StartCountLabel(
            modifier = Modifier,
            dDay = "무료", week = "4주동안", numberOfTimes = "매일"
        )
        Spacer(modifier = Modifier.width(10.dp))
        StartCountLabel(
            modifier = Modifier,
            dDay = "무료", week = "4주동안", numberOfTimes = "매일"
        )
    }
}

@Composable
fun StartCountLabel(
    modifier: Modifier = Modifier,
    dDay: String,
    week: String,
    numberOfTimes: String,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = Color(0xfff5f5f5)
    ) {
        Text(
            modifier = Modifier,
            text = dDay,
            style = myTypography.bold,
            fontSize = dpToSp(dp = 13.dp)
        )
        Text(
            modifier = Modifier,
            text = week,
            style = myTypography.bold,
            fontSize = dpToSp(dp = 13.dp)
        )
        Text(
            modifier = Modifier,
            text = numberOfTimes,
            style = myTypography.bold,
            fontSize = dpToSp(dp = 13.dp)
        )
    }
}