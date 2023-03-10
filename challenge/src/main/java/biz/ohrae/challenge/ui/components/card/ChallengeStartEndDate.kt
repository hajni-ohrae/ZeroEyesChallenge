package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ChallengeCardItemGallery() {
    Column(Modifier.background(DefaultBlack)) {
        ChallengeStartEndDateCard()
    }
}

@Composable
fun ChallengeStartEndDateCard(
    title:String ="챌린지 종료일",
    day : String ="",
    dayColor:Color = Color(0xffff5800)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(0xfff8f8f8),
        elevation = 0.dp,){
        Row(
            modifier = Modifier.padding(16.dp,20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, style = myTypography.w700, fontSize = dpToSp(dp = 14.dp), color = Color(0xff6c6c6c))
            Text(text = day, style = myTypography.w700, fontSize = dpToSp(dp = 14.dp), color = dayColor)
        }

    }
}