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
import biz.ohrae.challenge_repo.util.prefs.Utils
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
    val inChallenge = challengeData?.inChallenge?.get(0)
    val label = challengeData?.let { Utils.getRefundMethod(it) }
    val refund = if (challengeData?.free_rewards.isNullOrEmpty()) {
        if (inChallenge?.refund_amount!! > 0) {
            "${
                Utils.numberToString(inChallenge?.refund_amount.toString())

            }??? ??????????????????!"
        } else {
            ""
        }
    } else {
        "${challengeData?.inChallenge?.get(0)?.achievement_percent}%??? ??????????????????!"
    }


    val startDate by remember { mutableStateOf(Utils.convertDate6(challengeData?.start_date.toString())) }
    val endDate by remember { mutableStateOf(Utils.convertDate6(challengeData?.end_date.toString())) }
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
                text = refund,
                fontSize = dpToSp(dp = 20.dp),
                style = myTypography.extraBold,
            )
            if (label!!.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                ProgressLabel(
                    modifier = Modifier.align(CenterHorizontally),
                    text = label,
                    backgroundColor = Color(0xfff3f8ff),
                    textColor = Color(0xff4985f8)
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "????????? ??????", fontSize = dpToSp(dp = 16.dp),
                    style = myTypography.extraBold,
                )
                Text(
                    text = "$startDate ~ $endDate",
                    fontSize = dpToSp(dp = 16.dp),
                    style = myTypography.bold,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "?????? ??????", fontSize = dpToSp(dp = 16.dp),
                    style = myTypography.extraBold,
                )
                Text(
                    text = "${challengeData?.inChallenge?.get(0)?.ranking ?: ""} ??? ",
                    fontSize = dpToSp(dp = 16.dp),
                    style = myTypography.bold,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "?????? ?????????", fontSize = dpToSp(dp = 16.dp),
                    style = myTypography.extraBold,
                )
                Text(
                    text = "${challengeData?.inChallenge?.get(0)?.achievement_percent}%",
                    fontSize = dpToSp(dp = 16.dp),
                    style = myTypography.bold,
                    color = Color(0xffff5800)
                )

            }
            if (challengeData?.is_verification_time == 1) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "?????? ????????????", fontSize = dpToSp(dp = 16.dp),
                        style = myTypography.w500,
                    )
                    Text(
                        text = Utils.getTimeDays(challengeData.inChallenge?.get(0)?.verification_time.toString()),
                        fontSize = dpToSp(dp = 16.dp),
                        style = myTypography.bold,
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
            if (verificationState != null && !verificationState.verifications.isNullOrEmpty()) {
                ChallengeProgressFinishDetail(verificationState)
            }

            MyRewardsAndResults(challengeData)
            Spacer(modifier = Modifier.height(22.dp))
            FlatBorderButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                text = "???????????? ?????? ??????",
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
            text = "?????? ??????", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "????????????", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${challengeData?.summary?.total_user_cnt}???", fontSize = dpToSp(dp = 14.dp),
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
                text = "?????? ?????????", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${challengeData?.summary?.total_achievement_percent ?: "0.00"}%",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (challengeData?.is_verification_photo == 0) {
            val title = if (challengeData.is_verification_checkin == 1) "?????? ?????????" else "?????? ????????????"
            val content =
                if (challengeData.is_verification_checkin == 1) {
                    "${challengeData.summary?.average_verification_cnt ?: 0}???"
                } else {
                    Utils.getTimeDays(challengeData.summary?.average_verification_time.toString())
                }
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
    if (challengeData?.free_rewards.isNullOrEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "?????? ?????????", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${Utils.numberToString(challengeData?.summary?.total_amount.toString())}???",
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
                text = "?????? ?????????", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${Utils.numberToString(challengeData?.summary?.total_rewards_amount.toString())}???",
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
                text = "1?????? ?????????", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "(1?????? ??????) ${Utils.numberToString(challengeData?.summary?.per_rewards_amount.toString())}???",
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
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                text = "???????????? ${verificationState.successCount}???",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "???????????? ${verificationState.failCount}???",
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
                            number = (item.day + 1).toString()
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
    }
}

@Composable
fun MyReWardInfo(
    challengeData: ChallengeData? = null
) {
    val inChallenge = challengeData?.inChallenge?.get(0)
    Spacer(modifier = Modifier.height(16.dp))
    if (!challengeData?.free_rewards.isNullOrEmpty()) {
        if (challengeData?.inChallenge?.get(0)?.is_rewards == 1) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xffebebeb))
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "?????? ?????????", fontSize = dpToSp(dp = 16.dp),
                style = myTypography.bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "??????", fontSize = dpToSp(dp = 14.dp),
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
                    text = "?????? ??????", fontSize = dpToSp(dp = 14.dp),
                    style = myTypography.w500,
                )
                Text(
                    text = "${challengeData?.free_rewards_offer_way ?: ""}",
                    fontSize = dpToSp(dp = 14.dp),
                    style = myTypography.w500,
                )
            }
        }
//        Spacer(modifier = Modifier.height(16.dp))
    } else {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xffebebeb))
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "?????? ?????????", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "?????????", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
            Text(
                text = "${Utils.numberToString(inChallenge?.deposit_amount.toString())} ???",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
            )
        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                text = "?????????", fontSize = dpToSp(dp = 14.dp),
//                style = myTypography.w500,
//            )
//            Text(
//                text = "${Utils.numberToString(inChallenge?.refund_amount.toString())} ???",
//                fontSize = dpToSp(dp = 14.dp),
//                style = myTypography.w500,
//            )
//        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "?????????", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.w500,
                color = Color(0xffff5800)
            )
            Text(
                text = "${Utils.numberToString(inChallenge?.refund_amount.toString())} ???",
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
                text = "?????????", fontSize = dpToSp(dp = 14.dp),
                style = myTypography.bold,
                color = Color(0xff4985f8)
            )
            Text(
                text = "${Utils.numberToString(inChallenge?.rewards_amount.toString())} ???",
                fontSize = dpToSp(dp = 14.dp),
                style = myTypography.bold,
                color = Color(0xff4985f8)
            )
        }
    }
}