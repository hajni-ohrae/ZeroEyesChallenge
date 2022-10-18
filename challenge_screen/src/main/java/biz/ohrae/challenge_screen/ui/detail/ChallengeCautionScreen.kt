package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.text.NumberSectionText
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengeCautionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp, 0.dp)
            .background(DefaultWhite)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "리워즈의 정정, 취소 및 소멸",
            fontSize = dpToSp(dp = 20.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        NumberSectionText(
            number = 1,
            text = "리워즈 획득이 확정된 후 오류가 발생한 경우 회원은 오류 발생일로부터 30일 이내에 회사에 정정 요구를 할 수 있으며, 회사는 정당한 요구임이 확인된 경우 정정 요구일로부터 90일 이내에 정정 가능",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        NumberSectionText(
            number = 2,
            text = "클라이언트 변경, 해킹, 매크로 등 부정한 방법으로 리워즈를 획득한 경우 당 회 획득한 리워즈 뿐만 아니라 누적 지급된 모든 리워즈가 소멸되며, 회원 자격 상실 및 손해배상 청구",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        NumberSectionText(
            number = 3,
            text = "리워즈는 획득일로부터 730일간 유효하며, 730일 이내 현금으로 인출하지 않을 경우 적립된 순서대로 순차 소멸",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
    }
}