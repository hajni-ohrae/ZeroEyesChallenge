package biz.ohrae.challenge.ui.components.card

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
        CategorySurFace(
            modifier = Modifier,
            text = "무료",
            backgroundColor = Color(0x335c94ff),
            textColor = Color(0xff5c94ff)
        ) {
        }
        Spacer(modifier = Modifier.width(20.dp))

        CategorySurFace(
            modifier = Modifier,
            text = "사진인증",
            backgroundColor = Color(0xffdedede),
            textColor = Color(0xff7c7c7c)
        ) {
        }
        Spacer(modifier = Modifier.width(20.dp))
        CategorySurFace(
            modifier = Modifier,
            text = "18세미만 참여불가",
            backgroundColor = appColor.PointColor200,
            textColor = appColor.PointColor500
        ) {
        }
    }
}

@Composable
fun CategorySurFace(
    modifier: Modifier = Modifier,
    text: String = "무료",
    textStyle: TextStyle = myTypography.w700,
    backgroundColor: Color,
    textColor: Color,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        color = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(10.dp, 7.dp),
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}