package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun PolicyScreen(
) {
    Column() {
        ReaCardScreen()
    }
}

@Composable
fun ReaCardScreen(){
    Column() {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "레드카드 발급", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "제로아이즈 에서는 공정성을 위해 \n레드카드를 발급하고 있어요",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            color = Color(0xffff0000)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "· 인증규정관 무관한 이미지로 인증을 대체하려 한 경우\n· 신체 일부가 나와야 할때 본인이 아닌경우",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 16.dp),
            lineHeight = dpToSp(dp = 19.6.dp),
        )
        Spacer(modifier = Modifier.height(51.dp))
        Text(text = "패널티", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "1. 제로아이즈로부터 경고(레드카드)를 1회 이상 받은\n회원은 당 회 챌린지에서 인증이 1회 이상 실패처리",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 14.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "2. 경고(레드카드)를 2회 받은 회원은 당 회 챌린지에서\n인증이 1회 실패처리 될 뿐만 아니라 서비스 사용 이\n후 회원이 누적 수령한 모든 리워즈가 소멸",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 14.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "3. 경고(레드카드)를 3회 받은 회원은 영구적으로 챌린\n지 서비스에 대한 이용 정지",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 14.dp)
        )
    }
}