package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.*

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun CategorySurFaceGallery() {
    Row(modifier = Modifier
        .background(DefaultWhite)) {

        PaidFilterCard(modifier = Modifier, text = "전체", select = true)
        Spacer(modifier = Modifier.width(20.dp))
        PaidFilterCard(modifier = Modifier, text = "유료",)
        Spacer(modifier = Modifier.width(20.dp))
        PaidFilterCard(modifier = Modifier, text = "무료",)
    }
}

@Composable
fun PaidFilterCard(
    modifier: Modifier = Modifier,
    text: String = "무료",
    textStyle: TextStyle = myTypography.w700,
    select: Boolean = false,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        backgroundColor = if (select) Color(0xff005bad) else DefaultWhite,
        border = if (select)  BorderStroke(1.dp, Color(0xff005bad)) else  BorderStroke(1.dp, GrayColor5)
    ) {
        Text(
            modifier = Modifier.padding(20.dp, 12.dp),
            text = text,
            style = textStyle,
            color = if (select) DefaultWhite else TextBlack
        )
    }
}