package biz.ohrae.challenge.ui.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun MenuItemGallery() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(24.5.dp, 0.dp)) {
        MenuItem(
            modifier = Modifier
                .fillMaxWidth(),
            resId = R.drawable.icon_coin,
            title = "보유 리워즈",
            description = "125,000원",
            onClick = {}
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth(),
            resId = R.drawable.icon_card,
            title = "결제내역",
            onClick = {}
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth(),
            resId = R.drawable.icon_like,
            title = "저장한 챌린지",
            onClick = {}
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth(),
            resId = R.drawable.icon_note,
            title = "레드카드",
            onClick = {}
        )
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    resId: Int,
    title: String,
    description: String? = null,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable {
        onClick()
    }) {
        Row(
            modifier = Modifier.padding(0.dp,20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxWidth(0.077f).height(25.5.dp),
                painter = painterResource(id = resId),
                contentDescription = resId.toString(),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.5.dp))
            Text(
                text = title,
                style = myTypography.default,
                color = TextBlack,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            if (description != null) {
                Text(
                    text = description,
                    style = myTypography.default,
                    color = Color(0xffff5800),
                    fontSize = dpToSp(dp = 14.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.5.dp))
            Icon(
                painter = painterResource(id = R.drawable.icon_black_arrow_1),
                contentDescription = resId.toString(),
                tint = Color.Unspecified
            )
        }
        Divider(
            modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xfff6f6f6))
        )
    }
}