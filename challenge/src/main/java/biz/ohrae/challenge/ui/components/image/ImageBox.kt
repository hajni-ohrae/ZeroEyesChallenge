package biz.ohrae.challenge.ui.components.image

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge_component.R
import com.skydoves.landscapist.glide.GlideImage
import timber.log.Timber


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ImageBoxGallery(
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ImageBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.51f),
            imagePath = ""
        )
        Spacer(modifier = Modifier.height(20.dp))
        ImageBoxWithExampleTitle(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.35f),
            imagePath = "",
            isGood = true
        )
    }
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
        contentScale = ContentScale.Inside,
        failure = {
            Timber.e("fail??")
        }
    )
}

@Composable
fun ImageBoxWithExampleTitle(
    modifier: Modifier = Modifier,
    imagePath: String,
    isGood: Boolean,
) {
    var title = "좋은예시"
    val resId = if (isGood) {
        R.drawable.img_good
    } else {
        title = "나쁜예시"
        R.drawable.img_bad
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = resId),
                contentDescription = resId.toString(),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                fontSize = dpToSp(dp = 14.dp),
                color = TextBlack
            )
        }
        ImageBox(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            imagePath = imagePath
        )
    }
}