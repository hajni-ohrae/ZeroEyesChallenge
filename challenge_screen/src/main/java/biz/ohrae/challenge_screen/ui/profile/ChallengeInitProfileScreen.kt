import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatButton
import biz.ohrae.challenge.ui.components.input.TextBoxWithButton
import biz.ohrae.challenge.ui.components.profile.UploadProfileImage
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_screen.ui.profile.ChallengeProfileClickListener

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengeInitProfileScreen(
    user: User = User.mock(),
    profileImageUri: Uri? = null,
    clickListener: ChallengeProfileClickListener? = null,
) {
    val scrollState = rememberScrollState()
    Column(
         modifier = Modifier
             .fillMaxSize()
             .verticalScroll(scrollState)
    ) {
        UploadProfileImage(
            profileImageUri = profileImageUri,
            onClickUpload = {
                clickListener?.onClickProfileImage()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(24.dp, 0.dp)
        ) {
            var nickname by remember { mutableStateOf("") }

            Text(
                text = "닉네임*",
                fontSize = dpToSp(dp = 16.dp),
                color = TextBlack,
                style = myTypography.extraBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextBoxWithButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7.09f),
                placeholder = "닉네임(10자리)",
                maxLength = 10,
                value = nickname,
                onValueChange = {
                    nickname = it
                },
                buttonName = "중복확인",
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "닉네임은 한글, 영문, 숫자, _ . - 만 입력 가능",
                fontSize = dpToSp(dp = 12.dp),
                color = Color(0xff707070),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "이미 사용중인 닉네임입니다",
                fontSize = dpToSp(dp = 12.dp),
                color = Color.Red,
            )
            Spacer(modifier = Modifier.weight(1f))
            FlatButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6.5f),
                text = "완료",
                backgroundColor = Color(0xff003865),
                enabled = false,
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
