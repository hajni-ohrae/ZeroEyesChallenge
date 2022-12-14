package biz.ohrae.challenge_screen.ui.participation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatDoubleButton
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.participation.ParticipationResult
import biz.ohrae.challenge_repo.util.prefs.Utils

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ParticipationCancelResultScreen(
    challengeData: ChallengeData = ChallengeData.mock(),
    cancelResult: ParticipationResult = ParticipationResult.mock(),
    clickListener: ParticipationClickListener? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultWhite)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp, 0.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "챌린지 참여금이 정상적으로 \n" +
                        "환불 요청 되었습니다",
                color = TextBlack,
                fontSize = dpToSp(dp = 20.dp),
                lineHeight = dpToSp(dp = 25.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "취소내역",
                color = TextBlack,
                fontSize = dpToSp(dp = 18.dp),
                style = myTypography.bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "결제수단",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 16.dp),
                    color = Color(0xffc6c6c6)
                )
                Text(
                    text = Utils.getPaidMethod(cancelResult),
                    style = myTypography.default,
                    fontSize = dpToSp(dp = 16.dp),
                    color = TextBlack
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "카드 결제 취소",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 16.dp),
                    color = Color(0xffc6c6c6)
                )
                Text(
                    text = "${Utils.numberFormat(cancelResult.paid_amount)}원",
                    style = myTypography.default,
                    fontSize = dpToSp(dp = 16.dp),
                    color = TextBlack
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "리워즈 결제 취소",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 16.dp),
                    color = Color(0xffc6c6c6)
                )
                Text(
                    text = "${Utils.numberFormat(cancelResult.rewards_amount)}원",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 16.dp),
                    color = TextBlack
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb)))
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "총 취소금액",
                    style = myTypography.default,
                    fontSize = dpToSp(dp = 16.dp),
                    color = TextBlack
                )
                Text(
                    text = "${Utils.numberFormat(cancelResult.total_deposit_amount)}원",
                    style = myTypography.default,
                    fontSize = dpToSp(dp = 16.dp),
                    color = Color(0xff4985f8)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "카드사 사정에 따라 환불 처리까지\n" +
                        "영업일 기준 최대 4-5일이 소요될 수 있습니다",
                color = Color(0xff6c6c6c),
                fontSize = dpToSp(dp = 14.dp),
                lineHeight = dpToSp(dp = 22.dp)
            )

        }
        FlatDoubleButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            leftText = "결제상세 보기",
            rightText = "확인",
            onClickLeft = {
                clickListener?.onClickPaymentDetail()
            },
            onClickRight = {
                clickListener?.onClickCancelResult()
            }
        )
    }
}
