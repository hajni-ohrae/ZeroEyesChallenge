package biz.ohrae.challenge.ui.components.list_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
private fun RedCardHistoryItemGallery() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp)
    ) {
        RedCardHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 99.dp),
            date = "2022.04.25  09:33",
            title = "책읽기 챌린지",
            content = "인증규정관 무관한 이미지로 인증을 대체"
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xfffafafa)))
        RedCardHistoryItem(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 99.dp),
            date = "2022.04.25  09:33",
            title = "책읽기 챌린지",
            content = "인증규정관 무관한 이미지로 인증을 대체인증규정관 무관한 이미지로 인증을 대체인증규정관 무관한 이미지"
        )
    }
}

@Composable
fun RedCardHistoryItem(
    modifier: Modifier = Modifier,
    date: String,
    title: String,
    content: String
) {
    Column(
        modifier = modifier.padding(0.dp, 16.dp)
    ) {
        Text(
            text = date,
            style = myTypography.default,
            color = Color(0xff6c6c6c),
            fontSize = dpToSp(dp = 14.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = myTypography.bold,
            color = TextBlack,
            fontSize = dpToSp(dp = 14.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = content,
            style = myTypography.default,
            color = TextBlack,
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 16.8.dp)
        )
    }
}