package biz.ohrae.challenge.ui.components.label

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.appColor
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ChallengeDurationLabelGallery() {
    Column(
        modifier = Modifier.fillMaxWidth()) {
        ChallengeDurationLabel(
            modifier = Modifier,
            dDay = "오늘부터 시작", week = "4주동안", numberOfTimes = "매일"
        )
        Spacer(modifier = Modifier.height(10.dp))
        ChallengeDurationLabel(
            modifier = Modifier.fillMaxWidth(),
            dDay = "내일부터 시", week = "3주동안", numberOfTimes = "주말만"
        )
        Spacer(modifier = Modifier.height(10.dp))
        ChallengeDurationLabel(
            modifier = Modifier.fillMaxWidth(),
            dDay = "D-3", week = "1주동안", numberOfTimes = "주6회"
        )
    }
}
@Composable
fun ChallengeDurationLabel2(
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
        Row(
            modifier = Modifier.padding(20.dp,12.dp)) {
            Text(
                modifier = Modifier.weight(3F),
                text = week,
                textAlign = TextAlign.Center,
                style = myTypography.bold,
                fontSize = dpToSp(dp = 13.dp),
                color = DefaultBlack
            )
            Divider(modifier = Modifier
                .height(12.dp)
                .width(1.dp)
                .background(Color(0xfffafafa)))
            Text(
                modifier = Modifier.weight(3F),
                text = numberOfTimes,
                textAlign = TextAlign.Center,
                style = myTypography.bold,
                fontSize = dpToSp(dp = 13.dp),
                color = DefaultBlack
            )
            Divider(modifier = Modifier
                .height(12.dp)
                .width(1.dp)
                .background(Color(0xfffafafa)))
            Text(
                modifier = Modifier.weight(4F),
                text = dDay,
                textAlign = TextAlign.Center,
                color = Color(0xff005bad),
                style = myTypography.bold,
                fontSize = dpToSp(dp = 13.dp)
            )
        }

    }
}
@Composable
fun ChallengeDurationLabel(
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
        Row(
            modifier = Modifier.padding(20.dp,12.dp)) {
            Text(
                modifier = Modifier.weight(5F),
                text = dDay,
                textAlign = TextAlign.Center,
                color = Color(0xff005bad),
                style = myTypography.bold,
                fontSize = dpToSp(dp = 13.dp)
            )
            Divider(modifier = Modifier
                .height(12.dp)
                .width(1.dp)
                .background(Color(0xfffafafa)))
            Text(
                modifier = Modifier.weight(2.5F),
                text = "${week}주동안",
                textAlign = TextAlign.Center,
                style = myTypography.bold,
                fontSize = dpToSp(dp = 13.dp),
                color = DefaultBlack
            )
            Divider(modifier = Modifier
                .height(12.dp)
                .width(1.dp)
                .background(Color(0xfffafafa)))
            Text(
                modifier = Modifier.weight(2.5F),
                text = numberOfTimes,
                textAlign = TextAlign.Center,
                style = myTypography.bold,
                fontSize = dpToSp(dp = 13.dp),
                color = DefaultBlack
            )
        }
    }
}