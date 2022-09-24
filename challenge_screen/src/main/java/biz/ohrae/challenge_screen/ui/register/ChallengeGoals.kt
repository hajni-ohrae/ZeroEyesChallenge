package biz.ohrae.challenge_screen.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.input.TextBox
import biz.ohrae.challenge.ui.components.input.UploadPhotoBox
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_screen.model.register.ChallengeOpenState

@Preview(
    widthDp = 360,
    heightDp = 1000,
    showBackground = true,
)
@Composable
fun ChallengeGoals(
    challengeOpenState: ChallengeOpenState = ChallengeOpenState.mock(),
    challengeImageUri: String? = null,
    clickListener: RegisterClickListener? = null,
) {
    val scrollState = rememberScrollState()
    var authTitle by remember { mutableStateOf("") }
    var precautions by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(DefaultWhite)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp, 0.dp)
        ) {
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
                value = authTitle,
                onValueChange = {
                    authTitle = it
                }
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
                imageUri = challengeImageUri,
                onclick = {
                    clickListener?.onClickPhotoBox()
                }
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
                value = precautions,
                onValueChange = { precautions = it }
            )
        }
        Spacer(modifier = Modifier.height(53.dp))

        Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
            FlatBottomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                text = "개설완료",
                onClick = { clickListener?.onClickChallengeCreate(authTitle,precautions,"") }
            )
        }
    }
}

