package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.banner.FlatBanner
import biz.ohrae.challenge.ui.components.button.ArrowTextButton
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.list_item.RewardHistoryItem
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.user.RewardData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeActivity.Companion.REWARD
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun MyRewardScreen(
    user: User? = null,
    select: Boolean = true,
    clickListener: MyChallengeClickListener? = null,
    rewardList: List<RewardData>? = null,
) {
    val availableRewards by remember {
        mutableStateOf(user?.rewards_amount ?: 0)
    }
    Column() {
        Column(modifier = Modifier.padding(24.dp, 0.dp)) {
            Text(text = "보유 리워즈", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "5,000원 이상부터 출금 가능합니다\n" +
                        "오전10시 ~ 오후 3시 사이 출금 신청 가능합니다",
                style = myTypography.w700,
                fontSize = dpToSp(dp = 12.dp),
                color = Color(0xff828282),
                lineHeight = dpToSp(dp = 20.dp)
            )
            ArrowTextButton(
                text = "리워즈 정책 보러가기",
                onClick = { clickListener?.onClickPolicy(REWARD) }
            )
            FlatBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6.37f),
                backgroundColor = Color(0xfff3f8ff),
                title = "보유 리워즈",
                titleColor = TextBlack,
                content = "${Utils.numberToString(availableRewards.toString())}원",
                contentColor = Color(0xff005bad)
            )
            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "소멸 예정 리워즈 합계",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff6c6c6c)
                )
                Text(
                    text = "0원",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xffff5800)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xfffafafa))
            )
            if (rewardList.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "내역이 없습니다",
                        color = TextBlack,
                        fontSize = dpToSp(dp = 14.dp),
                        style = myTypography.bold
                    )
                }
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(33.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        crossAxisSpacing = 8.dp,
                        mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
                    ) {
                        repeat(rewardList.size) { index ->
                            val item = rewardList[index]
                            RewardHistoryItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 105.dp),
                                date = "",
                                progress = item.inChallenge.achievement_percent,
                                title = "",
                                price = "",
                                progressStatus = item.type,
                                background = Utils.userChallengeBackground(item.type),
                                textColor = Utils.userChallengeTextColor(item.type),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
            FlatBottomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f),
                text = "출금신청",
                onClick = {
                    clickListener?.onClickApplyWithdraw()
                }
            )
        }
    }

}