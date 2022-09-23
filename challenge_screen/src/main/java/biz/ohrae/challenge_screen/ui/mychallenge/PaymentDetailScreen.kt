package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_screen.ui.participation.ParticipationScreen


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun PaymentDetailScreen(
) {
    BackButton(onBack = { }, "결제 상세")
    Column(modifier = Modifier) {
        ParticipationScreen()
    }
    Column(modifier = Modifier.background(DefaultWhite)) {
        Column(modifier = Modifier.background(Color(0xfff8f8f8))) {
            Text(
                modifier = Modifier.padding(18.dp, 0.dp),
                text = "이용내역",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff707070)
            )
        }
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "챌린지",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "미라클 모닝, 일찍 일어나기",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "기간",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
        Column(modifier = Modifier.background(Color(0xfff8f8f8))) {
            Text(
                modifier = Modifier.padding(18.dp, 0.dp),
                text = "결제내역",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff707070)
            )
        }
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "카드결제",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "상금결제",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(24.dp, 0.dp)
                .background(Color(0xfffafafa))
        )
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "총 결제금액",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "결제수단",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "카드",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "결제일시",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
    }
}