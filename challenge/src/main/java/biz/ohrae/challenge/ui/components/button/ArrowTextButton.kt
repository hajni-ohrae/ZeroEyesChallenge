package biz.ohrae.challenge.ui.components.button

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun TextButtonGallery() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ArrowTextButton(
            text = "레드카드 정책 보러가기",
        )
        ArrowTextButton(
            text = "리워즈 정책 보러가기",
        )
        ArrowTextButton(
            text = "참여금 환급 정책 보러가기",
        )
        ArrowTextButton2(
            modifier = Modifier.fillMaxWidth().aspectRatio(3.71f),
            text = "챌린지 이용 주의사항",
            textColor = TextBlack
        )
    }
}

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
fun ArrowTextButton(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.w700,
    textColor: Color = Color(0xff005bad),
    textSize: Dp = 14.dp,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val iconSize by remember { mutableStateOf(textSize.value * 1.43f) }

    TextButton(
        modifier = modifier,
        onClick = {
            if (enabled) {
                onClick()
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = textStyle,
                color = textColor,
                fontSize = dpToSp(dp = textSize)
            )
            Icon(
                modifier = Modifier.size(iconSize.dp),
                painter = painterResource(id = R.drawable.icon_chevron_right_20),
                contentDescription = "icon_chevron_right_20",
                tint = textColor
            )
        }
    }
}

@Composable
fun ArrowTextButton2(
    modifier: Modifier = Modifier,
    text: String = "확인",
    textStyle: TextStyle = myTypography.w700,
    textColor: Color = Color(0xff005bad),
    textSize: Dp = 16.dp,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val iconSize by remember { mutableStateOf(textSize.value * 1.43f) }

    TextButton(
        modifier = modifier,
        onClick = {
            if (enabled) {
                onClick()
            }
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = textStyle,
                color = textColor,
                fontSize = dpToSp(dp = textSize)
            )
            Icon(
                modifier = Modifier.size(iconSize.dp),
                painter = painterResource(id = R.drawable.icon_chevron_right_20),
                contentDescription = "icon_chevron_right_20",
                tint = textColor
            )
        }
    }
}