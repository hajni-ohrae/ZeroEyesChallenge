package biz.ohrae.challenge.ui.components.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.R
import biz.ohrae.challenge.ui.theme.GrayColor7
import com.skydoves.landscapist.glide.GlideImage

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun AvatarGallery() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Avatar(
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    url: String = "https://avatars.githubusercontent.com/u/27887884?v=4",
    backgroundColor: Color? = GrayColor7
) {
    GlideImage(
        modifier = modifier.clip(CircleShape).background(backgroundColor!!),
        imageModel = url,
        contentScale = ContentScale.Crop,
        contentDescription = "avatar",
        previewPlaceholder = R.drawable.icon_user,
        failure = {
            Image(
                painter = painterResource(id = R.drawable.icon_user),
                modifier = modifier.clip(CircleShape),
                contentDescription = "avatar_fail"
            )
        }
    )
}