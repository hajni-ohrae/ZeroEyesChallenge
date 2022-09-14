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
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import kotlin.math.roundToInt


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun MyChallengeIngoBoxGallery() {
    Column() {
        MyChallengeIngoBox("12", "10")
    }
}

@Composable
fun MyChallengeIngoBox(challenge: String, achievements: String) {
    val average = (achievements.toDouble() / challenge.toDouble() * 100.0).roundToInt()

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        backgroundColor = Color(0xfff3f8ff)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 19.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "챌린지 참여", style = myTypography.bold, fontSize = dpToSp(dp = 14.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$challenge 회",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff005bad)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "목표 달성", style = myTypography.bold, fontSize = dpToSp(dp = 14.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$achievements 회",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff005bad)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "평균 달성률", style = myTypography.bold, fontSize = dpToSp(dp = 14.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$average %",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff005bad)
                )
            }
        }
    }
}