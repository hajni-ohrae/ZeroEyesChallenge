package biz.ohrae.challenge.ui.components.banner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
private fun FlatBannerGallery() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp)
    ) {
        FlatBanner(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6.37f),
            backgroundColor = Color(0xfffbefef),
            title = "레드카드",
            titleColor = Color(0xff6c6c6c),
            content = "2회",
            contentColor = Color(0xffff0000)
        )
        Spacer(modifier = Modifier.height(10.dp))
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
    }
}

@Composable
fun FlatBanner(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    title: String,
    titleColor: Color,
    content: String,
    contentColor: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = myTypography.bold,
                color = titleColor,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = content,
                style = myTypography.bold,
                color = contentColor,
                fontSize = dpToSp(dp = 14.dp)
            )
        }
    }
}