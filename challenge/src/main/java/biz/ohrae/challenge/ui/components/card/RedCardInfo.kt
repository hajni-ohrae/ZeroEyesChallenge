package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun RedCardInfoGallery() {
    Column() {
        RedCardInfo(
            modifier = Modifier
                .fillMaxWidth().aspectRatio(13f)
        )
    }
}

@Composable
fun RedCardInfo(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xffffeee5))
                    .padding(8.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape),
                    color = Color(0xffff5800)
                ) {
                    Text(
                        text = "!",
                        textAlign = TextAlign.Center,
                        color = DefaultWhite,
                        fontSize = dpToSp(dp = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "인증 규정을 지키지 않을 경우 레드카드가 발급됩니다",
                    color = TextBlack,
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 12.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = ">", style = myTypography.bold)
            }
        }
    }
}