package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.appColor

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun BottomCardGallery() {
    Column(modifier = Modifier.fillMaxWidth().background(appColor.PointColor)) {
        BottomSheetCard(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f)
        ) {

        }
    }
}

@Composable
fun BottomSheetCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
        backgroundColor = DefaultWhite,
        elevation = 5.dp,
        content = content
    )
}