package biz.ohrae.challenge.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
private fun ButtonGallery() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        FlatButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6.5f),
            text = "확인",
            backgroundColor = appColor.AlertSuccessColor
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlatButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6.5f),
            text = "다음",
            backgroundColor = appColor.AlertSuccessColor,
            enabled = false
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlatButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6.5f),
            text = "취소",
            backgroundColor = appColor.AlertWarningColor,
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlatBorderButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6.5f),
            text = "챌린저스 결과 보기",
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlatDoubleButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "참여 신청"
        )
    }
}

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
fun FlatButton(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.w700,
    backgroundColor: Color = Color(0xffeb712d),
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (enabled) backgroundColor else GrayColor7,
            contentColor = DefaultWhite
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            if (enabled) {
                onClick()
            }
        }
    ) {
        Text(
            text = text,
            style = textStyle,
            fontSize = dpToSp(dp = 14.dp)
        )
    }
}


@Composable
fun FlatBorderButton(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.bold,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = DefaultWhite,
            contentColor = TextBlack
        ),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color(0xffc7c7c7)),
        onClick = {
            if (enabled) {
                onClick()
            }
        }
    ) {
        Text(
            text = text,
            style = textStyle,
            fontSize = dpToSp(dp = 14.dp)
        )
    }
}

@Composable
fun FlatDoubleButton(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.bold,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    onClickLike: () -> Unit = {}
) {
    Row(
        modifier = modifier,
    ) {
        IconButton(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .background(Color(0xfffcd2d2)),
            onClick = {
                onClickLike()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_like),
                contentDescription = "icon_like"
            )
        }
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xff003865),
                contentColor = DefaultWhite
            ),
            shape = RectangleShape,
            onClick = {
                onClick()
            }
        ) {
            Text(
                text = text,
                style = textStyle,
                fontSize = dpToSp(dp = 18.dp)
            )
        }
    }
}
