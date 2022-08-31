package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.list_item.ChallengeItemData
import biz.ohrae.challenge.ui.components.avatar.Avatar
import biz.ohrae.challenge.ui.components.button.ArrowTextButton2
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.components.card.RedCardInfo
import biz.ohrae.challenge.ui.components.detail.ChallengeDetailRefundDescription
import biz.ohrae.challenge.ui.components.detail.ChallengeDetailsTitle
import biz.ohrae.challenge.ui.components.image.ImageBox
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1800
)
@Composable
fun ChallengeDetailBeforeJoinScreen() {
    val challengeItemData = ChallengeItemData.mock()
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .background(DefaultWhite)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 0.dp)
        ) {
            ImageBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.51f),
                imagePath = ""
            )
            Spacer(modifier = Modifier.height(24.dp))
            ChallengeDetailsTitle(
                challengeItemData.personnel,
                challengeItemData.title,
                challengeItemData.startDate,
                challengeItemData.endDate
            )
            Spacer(modifier = Modifier.height(32.dp))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb)))
            Spacer(modifier = Modifier.height(32.dp))
            ChallengeDescription()
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb)))
            ChallengeHost()
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb)))
            ArrowTextButton2(
                modifier = Modifier.fillMaxWidth().aspectRatio(3.71f),
                text = "챌린지 이용 주의사항",
                textColor = TextBlack
            )
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb)))
            Spacer(modifier = Modifier.height(96.dp))
        }
        FlatDoubleButton(
            modifier = Modifier.fillMaxWidth().aspectRatio(6f),
            text = "참여 신청"
        )
    }
}

@Composable
fun ChallengeDescription() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "왜 돈을 걸어야 하나요?",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        val annotatedString = buildAnnotatedString {
            append("확실한 동기부여를 위해서 돈을 걸어요\n" +
                    "챌린지를 시작하기 전에 돈을 걸고 \n" +
                    "내가 실천한 만큼 돌려받으면 끝까지 포기할 수가 없죠\n")
            withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                append("돈을 걸기 전과 후 달라진 나의 변화를 경험")
            }
            append("해보세요")
        }
        Text(
            text = annotatedString,
            style = myTypography.default,
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 21.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        ChallengeDetailRefundDescription(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.88f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "챌린지 진행시 꼭 확인하세요!",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "· 4주 동안 매일, 이용권 사용 내역이 이용시간으로 자동\n  인증됩니다.",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "· 인증 가능한 요일은 월,화,수,목,금,토,일 입니다",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "· 이용시간은 이용권으로 입실한 시점부터 퇴실까지의\n  시간이 자동 누적됩니다 (외출시간 포함)",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "· 인증샷 피드에 이용권 사용 시간이 공개됩니다",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        RedCardInfo(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(13f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "인증 방법 및 주의사항",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(13.dp))
        Text(
            text = "· 만화책은 안됩니다.",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "· 손은 어떤 제스처든 모두 가능해요.",
            fontSize = dpToSp(dp = 14.dp),
            lineHeight = dpToSp(dp = 19.6.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ChallengeHost() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "챌린지 호스트",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "박보검",
                style = myTypography.extraBold,
                fontSize = dpToSp(dp = 20.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}