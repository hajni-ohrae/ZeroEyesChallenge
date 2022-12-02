package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBorderButton
import biz.ohrae.challenge.ui.components.image.ImageBox
import biz.ohrae.challenge.ui.components.label.ProgressLabel
import biz.ohrae.challenge.ui.components.list_item.ProgressRatioFailItem
import biz.ohrae.challenge.ui.components.list_item.ProgressRatioItem
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.detail.Verification
import biz.ohrae.challenge_screen.model.detail.VerificationState
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode


@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1800
)
@Composable
fun ChallengeFinishedScreen(
    challengeData: ChallengeData? = null,
    verificationState: VerificationState? = null,
    clickListener: ChallengeDetailClickListener? = null
) {
    val scrollState = rememberScrollState()
    val reward = if(challengeData.)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(DefaultWhite)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp, 0.dp)
        ) {
            Spacer(modifier = Modifier.height(23.dp))
            Card(
                modifier = Modifier.align(CenterHorizontally), shape = RoundedCornerShape(10.dp),
            ) {
                ImageBox(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .aspectRatio(1f)
                        .background(TextBlack),
                    imagePath = challengeData?.imageFile?.path.toString(),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = challengeData?.goal.toString(), fontSize = dpToSp(dp = 16.dp),
                style = myTypography.w500,
                color = Color(0xff6c6c6c),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text =
                if (challengeData?.free_rewards.isNullOrEmpty())
                    "${challengeData?.inChallenge?.get(0)?.refund_amount}원 환급되었어요!"
                else
                    "${challengeData?.inChallenge?.get(0)?.achievement_percent}를 달성하였어요!",
                fontSize = dpToSp(dp = 20.dp),
                style = myTypography.extraBold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProgressLabel(
                modifier = Modifier.align(CenterHorizontally),
                text = "100% 환급 + 리워즈",
                backgroundColor = Color(0xfff3f8ff),
                textColor = Color(0xff4985f8)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "나의 달성률", fontSize = dpToSp(dp = 16.dp),
                    style = myTypography.extraBold,
                )
                Row() {
                    Text(
                        text = "${challengeData?.inChallenge?.get(0)?.ranking} 위 ",
                        fontSize = dpToSp(dp = 16.dp),
                        style = myTypography.bold,
                    )
                    Text(
                        text = "${challengeData?.inChallenge?.get(0)?.achievement_percent}%",
                        fontSize = dpToSp(dp = 16.dp),
                        style = myTypography.bold,
                        color = Color(0xffff5800)
                    )
                }
            }
            if (challengeData?.is_verification_time == 1) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "나의 이용시간", fontSize = dpToSp(dp = 16.dp),
                        style = myTypography.w500,
                    )
                    Text(
                        text = "${challengeData?.inChallenge?.get(0)?.verification_time} 위 ",
                        fontSize = dpToSp(dp = 16.dp),
                        style = myTypography.bold,
                    )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            if (verificationState != null) {
                ChallengeProgressFinishDetail(verificationState)
            }

            MyRewardsAndResults(challengeData)
            Spacer(modifier = Modifier.height(22.dp))
            FlatBorderButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7.1f),
                text = "챌린저스 결과 보기",
                onClick = {
                    clickListener?.onClickChallengersResults()
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

        }
    }

}

@Composable
private fun MyRewardsAndResults(
    challengeData: ChallengeData? = null,
) {
    Column() {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb))
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "나의 리워즈", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyReWardInfo(challengeData = challengeData)

        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb))
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "전체 결과", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "참여인원", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${challengeData?.summary?.total_user_cnt}명", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "평균 달성률", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${challengeData?.summary?.total_amount}명", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (challengeData?.is_verification_photo == 0) {
            val title = if (challengeData?.is_verification_checkin == 1) "평균 출석일" else "평균 이용시간"
            val content =
                if (challengeData?.is_verification_checkin == 1) "평균 출석일" else challengeData?.summary?.average_verification_time
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title, fontSize = dpToSp(dp = 14.dp),
                    style = myTypography.w500,
                )
                Text(
                    text = content.toString(), fontSize = dpToSp(dp = 14.dp),
                    style = myTypography.w500,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
    if (!challengeData?.free_rewards.isNullOrEmpty()){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "전체 참여금", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${challengeData?.summary?.total_amount}원", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "전체 리워즈", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${challengeData?.summary?.total_rewards_amount}원",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "1인당 리워즈", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "(1만원 기준) ${challengeData?.summary?.per_rewards_amount}원",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
    }
}


@Composable
private fun ChallengeProgressFinishDetail(
    verificationState: VerificationState
) {
    Column {
        Row {
            Text(
                text = "인증성공 ${verificationState.successCount}개",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "인증실패 ${verificationState.failCount}개",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        val spacing by remember { mutableStateOf(3.5.dp) }
        val itemSize = (LocalConfiguration.current.screenWidthDp.dp - 48.dp - (spacing * 9)) / 10

        FlowRow(
            mainAxisSize = SizeMode.Expand,
            crossAxisAlignment = FlowCrossAxisAlignment.Start,
            mainAxisSpacing = 3.5.dp,
            crossAxisSpacing = 3.5.dp
        ) {
            verificationState.verifications?.forEach { item ->
                when (item.status) {
                    Verification.REMAINING, Verification.SUCCESS -> {
                        ProgressRatioItem(
                            modifier = Modifier.size(itemSize),
                            isSuccess = item.status == Verification.SUCCESS,
                            number = item.day.toString()
                        )
                    }
                    Verification.FAILURE -> {
                        ProgressRatioFailItem(
                            modifier = Modifier.size(itemSize),
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun MyReWardInfo(
    challengeData: ChallengeData? = null
) {
    if (challengeData?.free_rewards.isNullOrEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "상품", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
                color = Color(0xff4985f8)
            )
            Text(
                text = "${challengeData?.free_rewards ?: ""}",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
                color = Color(0xff4985f8)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "지급 방법", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${challengeData?.free_rewards_offer_way ?: ""}",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
                color = Color(0xff4985f8)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "참여금", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${challengeData?.inChallenge?.get(0)?.deposit_amount} 원",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "달성금", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${challengeData?.inChallenge?.get(0)?.refund_amount} 원",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "환급금", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
                color = Color(0xffff5800)
            )
            Text(
                text = "${challengeData?.inChallenge?.get(0)?.refund_amount} 원",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
                color = Color(0xffff5800)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb))
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "리워즈", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.bold,
                color = Color(0xff4985f8)
            )
            Text(
                text = "${challengeData?.inChallenge?.get(0)?.rewards_amount} 원",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.bold,
                color = Color(0xff4985f8)
            )
        }
    }
}
@Composable
fun MyOverallResults(){

}
