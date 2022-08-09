package biz.ohrae.challenge.ui.components.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
fun AvatarGallery() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Avatar(
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
private fun Avatar(
    modifier: Modifier = Modifier,
    url: String = "https://avatars.githubusercontent.com/u/27887884?v=4"
) {
    GlideImage(
        modifier = modifier.clip(CircleShape),
        imageModel = url,
        contentScale = ContentScale.Crop,
        contentDescription = "avatar",
        previewPlaceholder = com.google.android.material.R.drawable.ic_clock_black_24dp,
        failure = {
            Image(
                painter = painterResource(id = com.google.android.material.R.drawable.ic_clock_black_24dp),
                modifier = modifier.clip(CircleShape),
                contentDescription = "avatar_fail"
            )
        }
    )
}