import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.profile.UploadProfileImage
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_screen.ui.profile.ChallengeProfileClickListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengeProfileScreen(
    user: User = User.mock(),
    profileImageUri: Uri? = null,
    clickListener: ChallengeProfileClickListener? = null,
) {
    val scrollState = rememberScrollState()
    Column(
         modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        UploadProfileImage(
            profileImageUri = profileImageUri,
            onClickUpload = {
                clickListener?.onClickProfileImage()
            }
        )
        SectionTitle(
            title = "닉네임 (챌린지 참여 시 사용됩니다)",
        )
        SectionContent(
            defaultValue = user.nickname ?: "",
            placeholder = "닉네임을 설정해주세요.",
            buttonName = "변경",
            defaultButtonEnabled = false,
            canEdit = true,
            maxLength = 10,
            onClickButton = {
                clickListener?.onClickChangeNickname(it)
            }
        )
        SectionTitle(
            title = "휴대폰 번호",
        )
        SectionContent(
            defaultValue = user.phone_number,
            placeholder = "휴대폰 번호를 설정해주세요.",
        )
        SectionTitle(
            title = "실명 (본인 인증과 동시에 자동 입력됩니다)"
        )
        SectionContent(
            defaultValue = user.name ?: "",
            placeholder = "본인 인증 필요",
            buttonName = "본인 인증",
            defaultButtonEnabled = true,
            canEdit = false,
            onClickButton = {
                clickListener?.onClickIdentityVerification()
            }
        )
        SectionTitle(
            title = "생년월일"
        )
        SectionContent(
            defaultValue = user.birth_date ?: "",
            placeholder = "생년월일을 설정해주세요.",
        )
        SectionTitle(
            title = "성별"
        )
        SectionContent(
            defaultValue = "",
            placeholder = "성별을 설정해주세요.",
        )
        SectionTitle(
            title = "인증 계좌"
        )
        SectionContent(
            defaultValue = "",
            placeholder = "등록된 계좌가 없습니다",
        )
    }
}

@Composable
private fun SectionTitle(title: String, caution: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(Color(0xfff8f8f8))
            .padding(24.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color(0xff707070),
            fontSize = dpToSp(dp = 14.dp),
            style = myTypography.bold
        )
        if (caution != null) {
            Text(
                text = title,
                color = Color.Red,
                fontSize = dpToSp(dp = 12.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun SectionContent(
    defaultValue: String,
    placeholder: String,
    buttonName: String? = null,
    defaultButtonEnabled: Boolean = false,
    canEdit: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    onClickButton: (value: String?) -> Unit = {},
    onDone: () -> Unit = {},
) {
    var enabled by remember { mutableStateOf(defaultButtonEnabled) }
    var readOnly by remember { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    var value by remember { mutableStateOf(defaultValue) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(Color.White)
            .padding(24.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BasicTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = value,
            textStyle = TextStyle(
                fontSize = dpToSp(dp = 16.dp),
                color = TextBlack,
                fontWeight = FontWeight.Bold
            ),
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    innerTextField = innerTextField,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = TextBlack,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    placeholder = {
                        Text(
                            text = placeholder,
                            color = Color(0xff6c6c6c),
                            style = myTypography.default
                        )
                    },
                    enabled = true,
                    singleLine = true,
                    interactionSource = remember { MutableInteractionSource() },
                    value = value,
                    visualTransformation = VisualTransformation.None,
                    contentPadding = PaddingValues(0.dp),
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            onValueChange = {
                if (it.length <= maxLength) {
                    value = it
                }
            },
            singleLine = true,
            readOnly = readOnly
        )
        if (buttonName != null) {
            val buttonColor = if (enabled) {
                Color(0xff005bad)
            } else {
                Color(0xffdedede)
            }

            val buttonTextColor = if (enabled) {
                DefaultWhite
            } else {
                Color(0xff707070)
            }

            Button(
                modifier = Modifier.height(32.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor,
                    contentColor = buttonTextColor
                ),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = {
                    if (canEdit) {
                        readOnly = enabled
                        enabled = !enabled
                        if (enabled) {
                            scope.launch {
                                delay(100)
                                focusRequester.requestFocus()
                                keyboardController?.show()
                            }
                        } else {
                            onClickButton(value)
                            onDone()
                            keyboardController?.hide()
                        }
                    } else {
                        if (enabled) {
                            onClickButton(null)
                        }
                    }
                }
            ) {
                Text(
                    text = if (canEdit && enabled) "저장" else buttonName,
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 12.dp)
                )
            }
        }
    }
}