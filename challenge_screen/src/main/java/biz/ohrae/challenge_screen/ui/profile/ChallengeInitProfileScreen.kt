import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatButton
import biz.ohrae.challenge.ui.components.input.TextBoxWithButton
import biz.ohrae.challenge.ui.components.profile.UploadProfileImage
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.profile.NicknameState
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_screen.ui.profile.ChallengeProfileClickListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengeInitProfileScreen(
    user: User = User.mock(),
    profileImageUri: Uri? = null,
    nicknameState: NicknameState? = null,
    clickListener: ChallengeProfileClickListener? = null,
) {
    val scrollState = rememberScrollState()
    Column(
         modifier = Modifier
             .fillMaxWidth()
             .fillMaxHeight()
             .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        var nickname by remember { mutableStateOf("") }

        TopArea(
            user = user,
            profileImageUri = profileImageUri,
            nicknameState = nicknameState,
            clickListener = clickListener,
            onValueChange = {
                nickname = it.replace(" ", "")
            },
            nickname = nickname
        )
        BottomArea(
            nicknameState = nicknameState,
            clickListener = clickListener,
            nickname = nickname
        )
    }
}

@Composable
private fun TopArea(
    user: User = User.mock(),
    profileImageUri: Uri? = null,
    nicknameState: NicknameState? = null,
    clickListener: ChallengeProfileClickListener? = null,
    nickname: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(24.dp, 0.dp)
    ) {
        UploadProfileImage(
            profileImageUri = profileImageUri,
            onClickUpload = {
                clickListener?.onClickProfileImage()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        val scope = rememberCoroutineScope()
        val focusRequester = remember { FocusRequester() }

        scope.launch {
            delay(100)
            focusRequester.requestFocus()
        }

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
                .aspectRatio(7.09f)
                .focusRequester(focusRequester),
            placeholder = "닉네임(10자리)",
            maxLength = 10,
            value = nickname,
            onValueChange = {
                onValueChange(it)
            },
            buttonName = "중복확인",
            singleLine = true,
            onClickButton = {
                clickListener?.onClickCheckNickname(nickname)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "닉네임은 한글, 영문, 숫자, _ . - 만 입력 가능",
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff707070),
        )
        if (nicknameState != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = nicknameState.message,
                fontSize = dpToSp(dp = 12.dp),
                color = if (nicknameState.success) Color(0xff36ad3b) else Color.Red,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun BottomArea(
    nicknameState: NicknameState? = null,
    clickListener: ChallengeProfileClickListener? = null,
    nickname: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        FlatButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6.5f),
            text = "완료",
            backgroundColor = Color(0xff003865),
            enabled = nicknameState?.success == true,
            onClick = {
                clickListener?.onClickChangeNickname(nickname)
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
