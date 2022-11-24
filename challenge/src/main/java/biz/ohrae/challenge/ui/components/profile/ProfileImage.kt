package biz.ohrae.challenge.ui.components.profile

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Composable
fun UploadProfileImage(
    profileImageUri: Uri?,
    onClickUpload: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(177.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(19.dp))
        circularAvatar(
            url = profileImageUri.toString(),
            modifier = Modifier.size(90.dp)
        )
        TextButton(
            contentPadding = PaddingValues(33.dp, 14.dp),
            onClick = {
                onClickUpload()
            },
        ) {
            Text(
                text = "사진업로드",
                color = Color(0xff003865),
                style = myTypography.bold,
                fontSize = dpToSp(dp = 18.dp)
            )
        }
    }
}