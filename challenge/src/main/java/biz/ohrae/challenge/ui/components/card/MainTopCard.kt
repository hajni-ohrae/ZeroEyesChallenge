package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun MainTopCardGallery() {
    Column() {
        MainTopCard(Modifier,"챌린지 이렇게\n이용해보세요")
    }
}

@Composable
fun MainTopCard(modifier: Modifier,content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9F),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(0xffed5f2a)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(
                text = content,
                style = myTypography.extraBold,
                fontSize = dpToSp(dp = 20.dp),
                color = DefaultWhite
            )
        }
    }
}