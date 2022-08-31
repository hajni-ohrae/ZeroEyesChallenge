package biz.ohrae.challenge_screen.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.register.ChallengeOpenState
import biz.ohrae.challenge.ui.components.input.TextBox
import biz.ohrae.challenge.ui.components.input.UploadPhotoBox
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true,
)
@Composable
fun ChallengeGoals(
    challengeOpenState: ChallengeOpenState = ChallengeOpenState.mock()
) {
    val selectedValue = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val maxChar = 60
    Column(modifier = Modifier.background(DefaultWhite)) {
        Text(
            text = "챌린지 목표",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 20.dp),
            color = DefaultBlack
        )
        Text(
            text = "달성하고자 하는 목표를 간단 명료하게 작성해주세요\n" +
                    "타인에게 불쾌감을 주는 단어나 이미지를 사용할 경우 계정이 \n" +
                    "영구정지 될 수 있습니다",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff828282)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "인증 빈도",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f),
        placeholder = "예) 주 2회 자전거 타고 인증하기",
        maxLength = 60,
        singleLine = true,
        value = "",
        onValueChange = {}
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "대표 이미지 등록",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(16.dp))
        UploadPhotoBox(
            modifier = Modifier
                .background(Color(0xfff8f8f8))
                .fillMaxWidth()
                .aspectRatio(1.316f),
            onclick = {}
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "인증방법 및 주의사항",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Text(
            text = "인증시 주의해야 할 사항이 있다면 작성해주세요",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 12.dp),
            color = Color(0xff828282)
        )
        TextBox(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.122f),
            placeholder = "예) 공부하는 책과 함께 손이 나온 사진 찍기\n" +
                    " \n" +
                    "- 만화책은 안됩니다\n" +
                    "- 손은 어떤 제스처든 모두 가능해요",
            maxLength = 1000,
            value = "",
            onValueChange = {}
        )
    }

}