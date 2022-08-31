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
fun ChallengeDetailRefundDescriptionGallery() {
    ChallengeDetailRefundDescription(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.88f)
    )
}

@Composable
fun ChallengeDetailRefundDescription(
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
            Text(
                text = "달성률 80%만 되어도 참가비 전액 환급!",
                style = myTypography.extraBold,
                color = Color(0xff005bad),
                fontSize = dpToSp(dp = 16.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.272f),
                    text = "100% 달성",
                    style = myTypography.extraBold,
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
                Text(
                    text = "참가비 전액 환급 + 성공 리워즈",
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
                    text = "80% 이상",
                    style = myTypography.extraBold,
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
                Text(
                    text = "참가비 전액 환급",
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
                    text = "80% 미만",
                    style = myTypography.extraBold,
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
                Text(
                    text = "성공률에 따라 부분 환급",
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
    }
}