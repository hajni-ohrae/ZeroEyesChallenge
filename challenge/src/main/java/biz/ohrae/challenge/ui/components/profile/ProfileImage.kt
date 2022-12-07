package biz.ohrae.challenge.ui.components.profile

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun UploadProfileImage(
    profileImageUri: Uri? = null,
    onClickUpload: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(177.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().clickable {
                onClickUpload()
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(19.dp))
            circularAvatar(
                url = profileImageUri.toString(),
                modifier = Modifier.size(90.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "사진업로드",
                color = Color(0xff003865),
                style = myTypography.bold,
                fontSize = dpToSp(dp = 18.dp)
            )
        }
    }
}