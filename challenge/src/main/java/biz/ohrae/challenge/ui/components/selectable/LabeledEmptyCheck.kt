package biz.ohrae.challenge.ui.components.selectable

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
private fun LabeledEmptyCheckGallery() {
    Column(modifier = Modifier.fillMaxWidth()) {
        LabeledCircleCheck(
            label = "전체 동의",
            checked = false,
            onClick = {}
        )
        LabeledCircleCheck(
            label = "전체 동의",
            checked = true,
            onClick = {}
        )
        LabeledEmptyCheck(
            label = "[필수] 개인정보 수집 및 이용 동의",
            checked = false,
            onClick = {}
        )
        LabeledEmptyCheck(
            label = "[필수] 본인확인을 위한 개인정보 처리 동의",
            checked = true,
            onClick = {}
        )
    }
}

@Composable
fun LabeledEmptyCheck(
    modifier: Modifier = Modifier,
    label: String,
    checked: Boolean,
    onClick: () -> Unit
) {
    val textColor = remember(checked) {
        if (checked) {
            TextBlack
        } else {
            Color(0xff6c6c6c)
        }
    }

    val iconColor = remember(checked) {
        if (checked) {
            Color(0xff005BAD)
        } else {
            Color(0xffc7c7c7)
        }
    }

    TextButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.check_empty),
                tint = iconColor,
                contentDescription = "check_empty"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = myTypography.bold,
                color = textColor,
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 16.8.dp)
            )
        }
    }
}

@Composable
fun LabeledCircleCheck(
    modifier: Modifier = Modifier,
    label: String,
    checked: Boolean,
    onClick: () -> Unit
) {
    val textColor = remember(checked) {
        if (checked) {
            TextBlack
        } else {
            Color(0xff6c6c6c)
        }
    }

    val iconId = remember(checked) {
        if (checked) {
            R.drawable.checked_circle
        } else {
            R.drawable.check_circle
        }
    }

    TextButton(
        modifier = modifier.defaultMinSize(
            minWidth = ButtonDefaults.MinWidth,
        ),
        contentPadding = PaddingValues(0.dp),
        onClick = { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                tint = Color.Unspecified,
                contentDescription = "check_empty"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = myTypography.bold,
                color = textColor,
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 16.8.dp)
            )
        }
    }
}

@Composable
fun LabeledRadioCheck(
    modifier: Modifier = Modifier,
    label: String,
    checked: Boolean,
    onClick: () -> Unit
) {

}