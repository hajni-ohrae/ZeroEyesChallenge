package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge.ui.components.banner.FlatBanner
import biz.ohrae.challenge.ui.components.button.ArrowTextButton
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.util.prefs.SharedPreference


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun MyRewardScreen(
    challengeData: ChallengeData = ChallengeData.mock(),
    prefs: SharedPreference? = null,
    select:Boolean = true,
    clickListener: MyChallengeClickListener? = null
) {
    Column() {
        Column(modifier = Modifier.padding(24.dp,0.dp)) {
            Text(text = "보유 리워즈", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
            Text(
                text = "5,000원 이상부터 출금 가능합니다\n" +
                        "오전10시 ~ 오후 3시 사이 출금 신청 가능합니다",
                style = myTypography.w700,
                fontSize = dpToSp(dp = 12.dp),
                color = Color(0xff828282)
            )
            ArrowTextButton(
                text = "리워즈 정책 보러가기",
            )
            FlatBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6.37f),
                backgroundColor = Color(0xfff3f8ff),
                title = "보유 리워즈",
                titleColor = TextBlack,
                content = "15,500원",
                contentColor = Color(0xff005bad)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "소멸 예정 리워즈",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff6c6c6c)
                )
                Text(
                    text = "2,500원",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xffff5800)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "소멸 일시",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff6c6c6c)
                )
                Text(
                    text = "2,500원",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff6c6c6c)
                )
            }

            Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
                FlatBottomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(6f),
                    text = "출금신청",
                    onClick = { }
                )
            }
        }
    }

}