package biz.ohrae.challenge.ui.components.image

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import biz.ohrae.challenge_component.R
import com.skydoves.landscapist.glide.GlideImage


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ImageBoxGallery(
) {
    ImageBox(
        modifier = Modifier.fillMaxWidth().aspectRatio(1.51f),
        imagePath = ""
    )
}

@Composable
fun ImageBox(
    modifier: Modifier = Modifier,
    imagePath: String,
) {
    GlideImage(
        modifier = modifier,
        imageModel = imagePath,
        previewPlaceholder = R.drawable.icon_camera,
        contentScale = ContentScale.Inside
    )
}