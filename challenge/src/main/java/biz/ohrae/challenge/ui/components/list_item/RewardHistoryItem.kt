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
import biz.ohrae.challenge.ui.components.label.ProgressLabel
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun RewardHistoryItemGallery() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        RewardHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 105.dp),
            date = "2022.04.25  09:33",
            progress = "100%",
            title = "미라클 모닝, 일찍 일어나기",
            price = "10,000원",
            progressStatus= "완료",
            background = Color(0xffdedede),
            textColor = Color(0xff6c6c6c)
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xfffafafa)))
        RewardHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 105.dp),
            date = "2022.04.25  09:33",
            progress = "85%",
            title = "달리기 챌린지",
            price = "1,000원",
            progressStatus= "완료",
            background = Color(0xffdedede),
            textColor = Color(0xff6c6c6c)
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xfffafafa)))
        RewardHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 105.dp),
            date = "2022.04.25  09:33",
            progress = "30%",
            title = "책읽기 챌린지",
            price = "10,000원",
            progressStatus= "완료",
            background = Color(0xffdedede),
            textColor = Color(0xff6c6c6c)
        )
    }
}

@Composable
fun RewardHistoryItem(
    modifier: Modifier = Modifier,
    date: String,
    progress: String,
    title: String,
    price: String,
    isCanceled: Boolean = false,

    progressStatus: String,
    background: Color,
    textColor: Color,
) {
    val priceColor = if (isCanceled) {
        remember { mutableStateOf(Color(0xff6c6c6c)) }
    } else {
        remember { mutableStateOf(Color(0xff4985f8)) }
    }

    Column(
        modifier = modifier.padding(24.dp, 0.dp,24.dp,18.dp)
    ) {

        ProgressLabel(
            text = progressStatus,
            backgroundColor = background,
            textColor = textColor
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = date,
                style = myTypography.default,
                color = TextBlack,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = progress,
                style = myTypography.default,
                color = Color(0xff6c6c6c),
                fontSize = dpToSp(dp = 14.dp)
            )
        }
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
                text = "$price 원",
                style = myTypography.bold,
                color = priceColor.value,
                fontSize = dpToSp(dp = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xfffafafa)))
//        Spacer(modifier = Modifier.height(8.dp))
//        if (state != null) {
//            Text(
//                text = state,
//                style = myTypography.default,
//                color = Color(0xff6c6c6c),
//                fontSize = dpToSp(dp = 14.dp)
//            )
//        }
    }
}