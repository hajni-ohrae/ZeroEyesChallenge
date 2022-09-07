package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
private fun RedCardInfoGallery() {
    Column() {
        RedCardInfo(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(13f)
        )
    }
}

@Composable
fun RedCardInfo(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(0.dp, Color(0xffffeee5))
        ) {
            val annotatedString = buildAnnotatedString {
                append("인증 규정을 지키지 않을 경우 ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold, color = Color(0xffff5800))) {
                    append("레드카드")
                }
                append("가 발급됩니다")
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xffffeee5))
                    .padding(8.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = R.drawable.icon_exclamation),
                    contentDescription = "icon_exclamation",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = annotatedString,
                    color = TextBlack,
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 12.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = ">",
                    style = myTypography.bold,
                    color = TextBlack,
                    fontSize = dpToSp(dp = 12.dp)
                )
            }
        }
    }
}