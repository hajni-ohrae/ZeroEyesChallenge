package biz.ohrae.challenge.ui.components.list_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.card.CategorySurFace
import biz.ohrae.challenge.ui.components.label.ProgressLabel
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import com.bumptech.glide.util.Util

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun PaidHistoryItemGallery() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PaidHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 105.dp),
            date = "2022.04.25  09:33",
            title = "책읽기 챌린지",
            amount = "0",
        )
        PaidHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 105.dp),
            date = "2022.04.25  09:33",
            title = "달리기 챌린지",
            amount = "0",
        )
        PaidHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 105.dp),
            date = "2022.04.25  09:33",
            title = "책읽기 챌린지",
            amount = "0",
        )
    }
}

@Composable
fun PaidHistoryItem(
    modifier: Modifier = Modifier,
    type: String = "",
    date: String,
    title: String,
    amount: String,
    reward: String = "",
    cardAmount: String = "",
    onClick: () -> Unit = {}
) {

    val isCanceled = type == "deposit"

    Column(
        modifier = modifier.clickable { onClick() }
    ) {
        Spacer(modifier = Modifier.height(19.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressLabel(
                text = getLabelText(type),
                backgroundColor = getLabelBackColor(type),
                textColor = getLabelTextColor(type),
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = date,
                style = myTypography.default,
                fontSize = dpToSp(dp = 14.dp),
                textAlign = TextAlign.Right,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(2f),
                text = title,
                style = myTypography.bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = TextBlack,
                fontSize = dpToSp(dp = 16.dp)
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Right,
                text = "${amount}원",
                style = myTypography.bold,
                color = getLabelTextColor(type),
                fontSize = dpToSp(dp = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isCanceled) "카드 결제" else "카드 결제 취소",
                style = myTypography.default,
                color = TextBlack,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (isCanceled) {
                    "${cardAmount}원"
                } else {
                    if (cardAmount == "0")
                        "${cardAmount}원"
                    else
                        "-${cardAmount}원"
                },
                style = myTypography.default,
                color = TextBlack,
                fontSize = dpToSp(dp = 14.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isCanceled) "리워즈 결제" else "리워즈 결제 취소",
                style = myTypography.default,
                color = TextBlack,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (isCanceled) {
                    "${reward}원"
                } else {
                    if (reward == "0")
                        "${reward}원"
                    else
                        "-${reward}원"
                },
                style = myTypography.default,
                color = TextBlack,
                fontSize = dpToSp(dp = 14.dp)
            )
        }

        Spacer(modifier = Modifier.height(19.dp))
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xfffafafa))
    )
}


fun getLabelText(type: String): String {
    return when (type) {
        "deposit" -> "참여신청"
        "cancel" -> "참여취소"
        "challenge_cancel" -> "챌린지 취소"
        "refund" -> "환급"
        else -> {
            ""
        }
    }
}

fun getLabelBackColor(type: String): Color {
    return when (type) {
        "deposit" -> Color(0xfff8f8f8)
        "cancel" -> Color(0xffffefef)
        "challenge_cancel" -> Color(0xffffefef)
        "refund" -> Color(0xfff9f3fd)
        else -> {
            Color(0xfff8f8f8)
        }
    }
}

fun getLabelTextColor(type: String): Color {
    return when (type) {
        "deposit" -> Color(0xff4985f8)
        "cancel" -> Color(0xffff0000)
        "challenge_cancel" -> Color(0xffff0000)
        "refund" -> Color(0xff9d00ff)
        else -> {
            Color(0xff4985f8)
        }
    }
}