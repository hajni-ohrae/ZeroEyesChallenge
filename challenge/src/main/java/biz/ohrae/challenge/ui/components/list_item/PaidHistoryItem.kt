package biz.ohrae.challenge.ui.components.list_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
private fun PaidHistoryItemGallery() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp)
    ) {
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
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xfffafafa)))
        PaidHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 105.dp),
            date = "2022.04.25  09:33",
            title = "달리기 챌린지",
            price = "1,000원",
            state = "상금결제",
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xfffafafa)))
        PaidHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 105.dp),
            date = "2022.04.25  09:33",
            title = "책읽기 챌린지",
            price = "10,000원",
            state = "카드결제 취소",
            isCanceled = true,
            cardInfo = "현대 1234"
        )
    }
}

@Composable
fun PaidHistoryItem(
    modifier: Modifier = Modifier,
    date: String,
    title: String,
    price: String,
    state: String,
    isCanceled: Boolean = false,
    cardInfo: String? = null,
) {
    val priceColor = if (isCanceled) {
        remember { mutableStateOf(Color(0xff6c6c6c)) }
    } else {
        remember { mutableStateOf(Color(0xff4985f8)) }
    }

    Column(
        modifier = modifier.padding(0.dp, 18.dp)
    ) {
        Text(
            text = date,
            style = myTypography.default,
            color = Color(0xff6c6c6c),
            fontSize = dpToSp(dp = 14.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = myTypography.bold,
                color = TextBlack,
                fontSize = dpToSp(dp = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = price,
                style = myTypography.bold,
                color = priceColor.value,
                fontSize = dpToSp(dp = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state,
                style = myTypography.default,
                color = TextBlack,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            if (!cardInfo.isNullOrEmpty()) {
                Text(
                    text = cardInfo,
                    style = myTypography.default,
                    color = TextBlack,
                    fontSize = dpToSp(dp = 14.dp)
                )
            }
        }
    }
}