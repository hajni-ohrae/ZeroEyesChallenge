package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.ArrowTextButton
import biz.ohrae.challenge.ui.components.list_item.PaidHistoryItem
import biz.ohrae.challenge.ui.components.list_item.RewardHistoryItem
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun PaymentDetailListScreen(
) {
    Column() {
        Column(modifier = Modifier.padding(24.dp, 0.dp)) {
            Text(text = "결제내역", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "챌린지 참여 시 결제하거나 달성 시 환급된 내역이 표시됩니다",
                style = myTypography.w500,
                fontSize = dpToSp(dp = 12.dp),
                color = Color(0xff828282)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ArrowTextButton(
                text = "참여금 환급 정책 보러가기",
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xfffafafa)))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                item {

                    PaidHistoryItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 105.dp),
                        date = "2022.04.25  09:33",
                        title = "책읽기 챌린지",
                        price = "10,000원",
                        state = "카드결제",
                        cardInfo = "현대 1234"
                    )
                }
            }
        }
    }
}