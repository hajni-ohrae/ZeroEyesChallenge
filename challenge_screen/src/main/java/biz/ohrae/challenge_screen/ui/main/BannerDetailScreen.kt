package biz.ohrae.challenge_screen.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_component.R


@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
fun BannerDetailScreen() {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .fillMaxSize()
        .background(DefaultWhite)) {
        Image(
            painter = painterResource(id = R.drawable.group_5953),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "frame_261"
        )
    }
}