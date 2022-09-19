package biz.ohrae.challenge_screen.ui.participation

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
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun CancelCompletedScreen(
) {
    Column() {
        Column(modifier = Modifier.padding(24.dp, 0.dp)) {
            Text(
                text = "챌린지 참여금이 정상적으로 \n환불 요청 되었습니다",
                style = myTypography.w700,
                fontSize = dpToSp(dp = 20.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "카드결제 취소 내역", style = myTypography.bold, fontSize = dpToSp(dp = 18.dp))
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "결제 수단",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "카",
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
                    text = "환불금액",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff4985f8)
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xfffafafa))
        )
        Text(
            text = "카드사 사정에 따라 환불 요청 후\n영업일 기준 최대 4-5일이 소요될 수 있습니다",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 14.dp),
            color = Color(0xff828282)
        )
    }
    Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
        FlatDoubleButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            leftText = "챌린지 알람 설정",
            rightText = "홈으로"
        )
    }

}