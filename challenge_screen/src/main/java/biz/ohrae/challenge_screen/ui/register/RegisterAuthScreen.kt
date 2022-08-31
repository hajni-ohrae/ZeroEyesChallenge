package biz.ohrae.challenge_screen.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.MainScreenState
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun RegisterAuthScreen(
) {
    Column(modifier = Modifier.padding(24.dp,0.dp)) {
        Text(text = "챌린지 인증", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
        Spacer(modifier = Modifier.height(16.dp))
        biz.ohrae.challenge.ui.components.button.RadioButton("Item", "사진인증", "즉석 촬영으로만 인증이 가능합니다")
        Spacer(modifier = Modifier.height(16.dp))
        biz.ohrae.challenge.ui.components.button.RadioButton("Item", "출석인증 (자동)", "입실 시 자동 인증됩니다")
        Spacer(modifier = Modifier.height(16.dp))
        biz.ohrae.challenge.ui.components.button.RadioButton("Item", "이용시간 인증 (자동)", "입실~퇴실 시간으로 자동 인증됩니다")
    }
}