package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
                painter = painterResource(id = R.drawable.top_banner),
                modifier = modifier
                    .aspectRatio(16f / 9F),
                contentDescription = "frame_261",
            )

        }
    }
}