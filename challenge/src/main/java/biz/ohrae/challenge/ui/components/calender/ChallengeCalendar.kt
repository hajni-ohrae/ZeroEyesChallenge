package biz.ohrae.challenge.ui.components.calender

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import java.text.SimpleDateFormat
import java.util.*

@Preview(
    showBackground = true,
    backgroundColor = 0xfff8f8f8,
    widthDp = 360
)
@Composable
private fun ChallengeCalendarCardGallery() {
    Column() {
        ChallengeCalendarCard()
    }

}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChallengeCalendarCard(
    today:String ="",
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(0xfff8f8f8),
        elevation = 0.dp,
        onClick = {}
    )
    {
        Row(
            modifier = Modifier.padding(16.dp,20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = today, style = myTypography.w700, fontSize = dpToSp(dp = 14.dp), color = Color(0xff6c6c6c))
        }

    }
}