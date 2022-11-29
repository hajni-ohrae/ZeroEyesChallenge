package biz.ohrae.challenge.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.detail.getRemainTime
import biz.ohrae.challenge.ui.components.label.ChallengeProgressStatus
import biz.ohrae.challenge.ui.theme.*
import biz.ohrae.challenge_component.R
import kotlinx.coroutines.delay

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
        FlatBookMarkButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "참여 신청"
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "참여 신청"
        )
        Spacer(modifier = Modifier.height(20.dp))

        FlatDoubleButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            leftText = "",
            rightText = ""
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
    Button(
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = DefaultWhite,
            contentColor = TextBlack
        ),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color(0xffc7c7c7)),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
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
fun FlatGrayButton(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.extraBold,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color(0xfff3f3f3),
            contentColor = TextBlack
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
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
    rightText: String = "확인",
    leftText: String = "",
    textStyle: TextStyle = myTypography.bold,
    enabled: Boolean = true,
    onClickRight: () -> Unit = {},
    onClickLeft: () -> Unit = {}
) {
    val rightBackgroundColor = if (enabled) {
        Color(0xff003865)
    } else {
        GrayColor6
    }

    Row(
        modifier = modifier,
    ) {
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xffdedede),
                contentColor = DefaultWhite
            ),
            shape = RectangleShape,
            onClick = {
                onClickLeft()
            }
        ) {
            Text(
                text = leftText,
                style = textStyle,
                fontSize = dpToSp(dp = 18.dp),
                color = Color(0xff121212)
            )
        }
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = rightBackgroundColor,
                contentColor = DefaultWhite
            ),
            shape = RectangleShape,
            onClick = {
                if (enabled) {
                    onClickRight()
                }
            }
        ) {
            Text(
                text = rightText,
                style = textStyle,
                fontSize = dpToSp(dp = 18.dp)
            )
        }
    }
}

@Composable
fun FlatBookMarkButton(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.bold,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    onClickBookMark: () -> Unit = {},
    checked: Boolean = false
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
                onClickBookMark()
            },
        ) {
            if (!checked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_like),
                    contentDescription = "icon_like"
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.icon_like_on),
                    contentDescription = "icon_like_on",
                    tint = Color.Unspecified,

                    )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (enabled) Color(0xff003865) else (Color(0xff6c6c6c)),
                contentColor = DefaultWhite
            ),
            shape = RectangleShape,
            onClick = {
                if(enabled) {
                    onClick()
                }
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

@Composable
fun FlatBottomButton(
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
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (enabled) Color(0xff003865) else GrayColor7,
                contentColor = DefaultWhite
            ),
            shape = RectangleShape,
            onClick = {
                if (enabled) {
                    onClick()
                }
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


@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
fun ChallengeStatusButton(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.w700,
    backgroundColor: Color = Color(0xffeb712d),
    textColor :Color = DefaultWhite,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    status:String = "",
    isRemainTime: Boolean = false,
    isFinished: Boolean = false,
) {
    val border = if (text == "인증하기" || text == "인증완료") BorderStroke(0.dp, Color(0xffc7c7c7)) else BorderStroke(1.dp, Color(0xffc7c7c7))
    TextButton(
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor ,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(10.dp),
        border = border,
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