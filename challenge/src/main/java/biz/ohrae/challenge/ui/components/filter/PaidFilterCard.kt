package biz.ohrae.challenge.ui.components.filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.*
import biz.ohrae.challenge_component.R

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun CategorySurFaceGallery() {
    Row(
        modifier = Modifier
            .background(DefaultWhite)
    ) {
        PaidFilterCard(modifier = Modifier, text = "전체", select = true)
        Spacer(modifier = Modifier.width(20.dp))
        PaidFilterCard(modifier = Modifier, text = "유료")
        Spacer(modifier = Modifier.width(20.dp))
        PaidFilterCard(modifier = Modifier, text = "무료")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PaidFilterCard(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = myTypography.w700,
    select: Boolean = false,
    icon: Int = R.drawable.icon_candle_2,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        backgroundColor = if (select) Color(0xff005bad) else DefaultWhite,
        border = if (select) BorderStroke(1.dp, Color(0xff005bad)) else BorderStroke(
            1.dp,
            GrayColor5
        ),

        onClick = { onClick() }
    ) {
        if (text?.isNotEmpty() == true) {
            Text(
                modifier = Modifier.padding(20.dp, 12.dp),
                text = text,
                style = textStyle,
                fontSize = dpToSp(dp = 14.dp),
                color = if (select) DefaultWhite else TextBlack
            )
        } else {
            Icon(
                modifier = Modifier.padding(16.dp, 7.dp),
                painter = painterResource(id = icon),
                contentDescription = "icon"
            )
        }

    }
}