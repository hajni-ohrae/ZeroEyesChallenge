package biz.ohrae.challenge_screen.ui.detail

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import biz.ohrae.challenge_repo.util.prefs.Utils
import com.skydoves.landscapist.glide.GlideImage
import java.util.*

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengeDetailAuthCameraResultScreen(
    imageUri: Uri? = null,
    clickListener: ChallengeDetailClickListener? = null,
) {
    val now by remember { mutableStateOf(Utils.sdf4().format(Date())) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TextBlack)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(24.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                GlideImage(
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                    imageModel = imageUri.toString(),
                    failure = {

                    },
                    previewPlaceholder = R.drawable.img_example,
                )
                Text(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(22.dp),
                    text = now,
                    fontSize = dpToSp(dp = 20.dp),
                    style = myTypography.extraBold,
                    color = DefaultWhite
                )
            }
        }
        FlatDoubleButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
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