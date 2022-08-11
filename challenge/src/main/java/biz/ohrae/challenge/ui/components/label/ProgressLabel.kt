package biz.ohrae.challenge.ui.components.label

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ProgressLabelGallery() {
    Row(modifier = Modifier.fillMaxWidth()) {
        ProgressLabel(text = "진행중", backgroundColor = Color(0xfff3f8ff), textColor = Color(0xff4985f8))
        Spacer(modifier = Modifier.width(10.dp))
        ProgressLabel(text = "모집중", backgroundColor = Color(0xffebfaf1), textColor = Color(0xff219653))
        Spacer(modifier = Modifier.width(10.dp))
        ProgressLabel(text = "완료", backgroundColor = Color(0xffdedede), textColor = Color(0xff6c6c6c))
    }
}

@Composable
fun ProgressLabel(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    textColor: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            style = myTypography.bold,
            color = textColor
        )
    }
}