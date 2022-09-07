package biz.ohrae.challenge.ui.components.label

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ChallengeProgressStatusGallery() {

    Column() {
        ChallengeProgressStatus(
            modifier = Modifier.fillMaxWidth(), text = "챌린지가 완료되었습니다.", borderStroke = BorderStroke(1.dp, Color(0xffececec))
        )
    }

}

@Composable
fun ChallengeProgressStatus(
    modifier: Modifier = Modifier,
    textColor: Color = Color(0xff6c6c6c),
    text: String = "",
    borderStroke: BorderStroke = BorderStroke(0.dp, Color(0xffececec)),
    backgroundColor: Color = DefaultWhite,
    isRemainTime: Boolean = false
) {
    val annotatedString = buildAnnotatedString {
        append(text)
        if (isRemainTime) {
            withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold, color = TextBlack)) {
                append(" 남음")
            }
        }
    }
    
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        border = borderStroke,
        color = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(0.dp, 12.dp),
            textAlign = TextAlign.Center,
            style = myTypography.extraBold,
            text = annotatedString,
            color = textColor
        )
    }
}