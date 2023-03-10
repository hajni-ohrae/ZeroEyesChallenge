package biz.ohrae.challenge.ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge_component.R
import com.skydoves.landscapist.glide.GlideImage


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
            resourceId = R.drawable.img_good_example,
            isGood = true
        )
    }
}

@Composable
fun ImageBox(
    modifier: Modifier = Modifier,
    imagePath: String,
    contentScale: ContentScale = ContentScale.Inside
) {
    GlideImage(
        modifier = modifier,
        imageModel = imagePath,
        previewPlaceholder = R.drawable.icon_camera,
        contentScale = contentScale,
        failure = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(TextBlack),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "???????????? ????????? ??? ????????????.",
                    color = DefaultWhite
                )
            }
        }
    )
}

@Composable
fun ImageBoxWithExampleTitle(
    modifier: Modifier = Modifier,
    resourceId: Int,
    isGood: Boolean,
) {
    var title = "????????????"
    val resId = if (isGood) {
        R.drawable.img_good
    } else {
        title = "????????????"
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
        Spacer(modifier = Modifier.height(9.dp))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            painter = painterResource(id = resourceId),
            contentDescription = resourceId.toString(),
            contentScale = ContentScale.FillBounds
        )
    }
}