package biz.ohrae.challenge.ui.components.card

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun MainTopCardGallery() {
    Column() {
        MainTopCard(Modifier, "챌린지 이렇게\n이용해보세요")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainTopCard(
    modifier: Modifier, content: String, onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        IconButton(
            modifier = modifier,
            onClick = {
                onClick()
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.frame_261),
                modifier = modifier
                    .aspectRatio(16f / 9F),
                contentDescription = "frame_261",
            )

        }
    }
}