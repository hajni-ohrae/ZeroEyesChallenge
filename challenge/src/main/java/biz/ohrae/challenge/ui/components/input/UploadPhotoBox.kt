package biz.ohrae.challenge.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
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
import com.skydoves.landscapist.glide.GlideImage

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun UploadPhotoBoxGallery() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DefaultWhite)
            .padding(24.dp)
    ) {
        UploadPhotoBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.316f),
            onclick = {}
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UploadPhotoBox(
    modifier: Modifier = Modifier,
    imageUri: String? = null,
    onclick: () -> Unit
) {
    Card(
        modifier = modifier.background(Color(0xfff8f8f8)),
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        onClick = {
            onclick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfff8f8f8)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!imageUri.isNullOrEmpty() && imageUri.toString() != "null") {
                GlideImage(imageModel = imageUri)
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_camera),
                        contentDescription = "icon_camera",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "사진업로드",
                        style = myTypography.bold,
                        color = Color(0xff003865),
                        fontSize = dpToSp(dp = 18.dp)
                    )
                }
            }
        }
    }
}