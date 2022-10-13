package biz.ohrae.challenge_screen.ui.register

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge_component.R
import com.skydoves.landscapist.glide.GlideImage

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengerCameraResultScreen(
    imageUri: Uri? = null,
    clickListener: RegisterClickListener? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TextBlack)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
           GlideImage(
               imageModel = imageUri,
               failure = {

               },
               previewPlaceholder = R.drawable.img_example,
           )
        }
        FlatDoubleButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            leftText = "재촬영",
            rightText = "인증샷 사용",
            onClickLeft = {
                clickListener?.onClickReTakePhoto()
            },
            onClickRight = {
                clickListener?.onClickUsePhoto()
            }
        )
    }
}