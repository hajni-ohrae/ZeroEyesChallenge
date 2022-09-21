package biz.ohrae.challenge.ui.components.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengeDetailFreeDescriptionGallery() {
    ChallengeDetailFreeDescription(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.88f)
    )
}

@Composable
fun ChallengeDetailFreeDescription(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = Color(0xfff8f8f8)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.272f),
                    text = "목표 당성률",
                    style = myTypography.extraBold,
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
                Text(
                    text = "90% 이상",
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.272f),
                    text = "상품",
                    style = myTypography.extraBold,
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
                Text(
                    text = "문화상품권 50,000원",
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.272f),
                    text = "지급 인원",
                    style = myTypography.extraBold,
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
                Text(
                    text = "90%이상 달성자중 추첨 10명",
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.272f),
                    text = "지급 방법",
                    style = myTypography.extraBold,
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
                Text(
                    text = "문자전송",
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
    }
}