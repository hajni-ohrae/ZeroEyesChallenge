package biz.ohrae.challenge.ui.components.avatar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge_component.R
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.GrayColor7
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
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
        circularAvatar(
            modifier = Modifier.size(40.dp)
        )
        AvatarWithNumber(
            modifier = Modifier.size(40.dp),
            number = "1",
        )
        AvatarWithNumber(
            modifier = Modifier.size(40.dp),
            number = "2",
            borderColor = Color(0xff6fcf97),
            numberBackgroundColor = Color(0xff1aaf5a)
        )
        AvatarWithNumber(
            modifier = Modifier.size(40.dp),
            number = "3",
            borderColor = Color(0xfff2994a),
            numberBackgroundColor = Color(0xffeb5757)
        )
    }
}

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    url: String = "https://avatars.githubusercontent.com/u/27887884?v=4",
) {
    Image(
        painter = painterResource(id = R.drawable.icon_user),
        modifier = modifier.clip(CircleShape),
        contentDescription = "avatar_fail"
    )
}

@Composable
fun circularAvatar(
    modifier: Modifier = Modifier,
    url: String = "",
    backgroundColor: Color? = Color(0xffeeeeee),
) {
    GlideImage(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor!!),
        imageModel = if (url.isNullOrEmpty()) R.drawable.profile_picture else url,
        contentScale = ContentScale.Crop,
        contentDescription = "avatar",
        previewPlaceholder = R.drawable.icon_user,
        failure = {
            Image(
                painter = painterResource(id = R.drawable.icon_user),
                modifier = modifier.clip(CircleShape),
                contentDescription = "avatar_fail"
            )
        },
    )
}

@Composable
fun AvatarWithNumber(
    modifier: Modifier = Modifier,
    url: String = "https://avatars.githubusercontent.com/u/27887884?v=4",
    number: String,
    backgroundColor: Color? = GrayColor7,
    borderColor: Color? = Color(0xfff2c94c),
    numberBackgroundColor: Color? = Color(0xfff2994a),
) {
    Box(modifier = modifier) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .border(BorderStroke(2.dp, borderColor!!), CircleShape)
                .clip(CircleShape)
                .background(backgroundColor!!),
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
        RoundWithNumber(
            modifier = Modifier.fillMaxSize(0.388f),
            number = number,
            backgroundColor = numberBackgroundColor,
            borderColor = borderColor
        )
    }
}

@Composable
fun RoundWithNumber(
    modifier: Modifier = Modifier,
    backgroundColor: Color? = Color(0xfff2c94c),
    borderColor: Color? = Color(0xfff2994a),
    number: String = "1"
) {
    Box(
        modifier = modifier
            .border(BorderStroke(1.dp, borderColor!!), CircleShape)
            .clip(CircleShape)
            .background(backgroundColor!!),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number,
            style = myTypography.bold,
            fontSize = dpToSp(dp = 8.dp),
            color = DefaultWhite,
            textAlign = TextAlign.Center,
        )
    }
}