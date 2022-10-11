package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun PolicyScreen(
    screen: String = ""
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DefaultWhite)
        .padding(24.dp, 0.dp)) {
        if (screen == "reward") {
            RewardPolicyScreen()
        } else {
            RedCardPolicyScreen()
        }
    }
}

@Composable
fun RewardPolicyScreen() {
    Column() {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "리워즈의 정정, 취소 및 소멸", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
        Spacer(modifier = Modifier.height(16.dp))
        numberText(
            "1.",
            "리워즈 획득이 확정된 후 오류가 발생한 경우 회원은\n" +
                    "오류 발생일로부터 30일 이내에 회사에 정정 요구를\n" +
                    "할 수 있으며, 회사는 정당한 요구임이 확인된 경우\n" +
                    "정정 요구일로부터 90일 이내에 정정 가능"
        )
        Spacer(modifier = Modifier.height(16.dp))
        numberText(
            "2.",
            "클라이언트 변경, 해킹, 매크로 등 부정한 방법으로\n" +
                    "리워즈를 획득한 경우 당 회 획득한 리워즈 뿐만 아\n" +
                    "니라 누적 지급된 모든 리워즈가 소멸되며, 회원 자\n" +
                    "격 상실 및 손해배상 청구"
        )
        Spacer(modifier = Modifier.height(16.dp))
        numberText(
            "3.", "리워즈는 획득일로부터 730일\n" +
                    "간 유효하며, 730일 이내 현금으로 인출하지 않을 경우 적립된 순서대로\n" +
                    "순차 소멸"
        )
    }
}

@Composable
fun RedCardPolicyScreen() {
    Column() {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "레드카드 발급", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "제로아이즈 에서는 공정성을 위해 \n레드카드를 발급하고 있어요",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 16.dp),
            lineHeight = dpToSp(dp = 22.dp),
            color = Color(0xffff0000)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "· 인증규정관 무관한 이미지로 인증을 대체하려 한 경우\n· 신체 일부가 나와야 할때 본인이 아닌경우",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 21.dp),
            color = Color(0xff121212)
        )
        Spacer(modifier = Modifier.height(51.dp))
        Text(text = "패널티", style = myTypography.w500, fontSize = dpToSp(dp = 20.dp))
        Spacer(modifier = Modifier.height(16.dp))
        numberText(
            "1.", "제로아이즈로부터 경고(레드카드)를 1회 이상 받은\n" +
                    "회원은 당 회 챌린지에서 인증이 1회 이상 실패처리"
        )
        Spacer(modifier = Modifier.height(16.dp))
        numberText(
            "2.",
            "경고(레드카드)를 2회 받은 회원은 당 회 챌린지에서\n" +
                    "인증이 1회 실패처리 될 뿐만 아니라 서비스 사용 이\n" +
                    "후 회원이 누적 수령한 모든 리워즈가 소멸"
        )
        Spacer(modifier = Modifier.height(16.dp))
        numberText("3.", "경고(레드카드)를 3회 받은 회원은 영구적으로 챌린\n지 서비스에 대한 이용 정지")
    }
}

@Composable
fun numberText(number: String, text: String) {
    Row() {
        Text(
            text = number,
            style = myTypography.w500,
            fontSize = dpToSp(dp = 14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = myTypography.w500,
            fontSize = dpToSp(dp = 14.dp)
        )
    }
}