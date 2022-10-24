package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.list_item.ProgressRatioFailItem
import biz.ohrae.challenge.ui.components.list_item.ProgressRatioItem
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_screen.model.detail.Verification
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
    challengeData: ChallengeData? = ChallengeData.mock(),
    verificationState: VerificationState? = null,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(DefaultWhite)
    ) {
        Text(
            text = challengeData?.goal.toString(), fontSize = dpToSp(dp = 16.dp),
            style = myTypography.w500,
            color = Color(0xff6c6c6c)
        )
        Text(
            text = "원 환급되었어요!", fontSize = dpToSp(dp = 20.dp),
            style = myTypography.extraBold,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "나의 달성률", fontSize = dpToSp(dp = 20.dp),
                style = myTypography.extraBold,
            )
            Row() {
                Text(
                    text = "위", fontSize = dpToSp(dp = 20.dp),
                    style = myTypography.bold,
                )
                Text(
                    text = "%", fontSize = dpToSp(dp = 20.dp),
                    style = myTypography.bold,
                    color = Color(0xffff5800)
                )
            }
        }
        if (verificationState != null) {
            ChallengeProgressFinishDetail(verificationState)
        }

        MyRewardsAndResults()

    }
}

@Composable
private fun MyRewardsAndResults
(
) {
    Column() {
        Spacer(modifier = Modifier.height(24.dp))
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "참여금", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "원", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "달성금", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "원", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "환급금", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
                color = Color(0xffff5800)
            )
            Text(
                text = "원", fontSize = dpToSp(dp = 14.dp),
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
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "리워즈", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.bold,
                color = Color(0xff4985f8)
            )
            Text(
                text = "원", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.bold,
                color = Color(0xffff5800)
            )
        }
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "참여인원", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "명", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "평균 달성률", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "명", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "전체 참여금", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "명", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "전체 리워즈", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "명", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "1인당 리워즈", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "", fontSize = dpToSp(dp = 14.dp),
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
        Text(
            text = "달성률",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 18.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
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
                when (item.state) {
                    Verification.NORMAL, Verification.SUCCESS -> {
                        ProgressRatioItem(
                            modifier = Modifier.size(itemSize),
                            isSuccess = item.state == Verification.SUCCESS,
                            number = item.day.toString()
                        )
                    }
                    Verification.FAIL -> {
                        ProgressRatioFailItem(
                            modifier = Modifier.size(itemSize),
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb))
        )
    }
}