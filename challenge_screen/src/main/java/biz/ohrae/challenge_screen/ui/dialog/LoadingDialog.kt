package biz.ohrae.challenge_screen.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge_screen.R
import com.skydoves.landscapist.glide.GlideImage

val IMAGE_SIZE = 150.dp

@Preview(
    widthDp = 800,
    showBackground = true,
)
@Composable
fun LoadingDialog() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if(LocalInspectionMode.current) {
            Image(
                modifier = Modifier.size(IMAGE_SIZE),
                painter = painterResource(id = R.drawable.loading),
                contentDescription = "loading"
            )
        } else {
            GlideImage(
                modifier = Modifier.size(IMAGE_SIZE),
                imageModel = R.drawable.loading,
            )
        }
    }
}