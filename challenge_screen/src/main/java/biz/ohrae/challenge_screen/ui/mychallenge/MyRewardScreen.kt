package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import biz.ohrae.challenge.ui.components.filter.PaidFilterCard
import biz.ohrae.challenge.ui.components.list_item.RewardHistoryItem
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.user.RewardData
import biz.ohrae.challenge_repo.model.user.RewardFilter
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.main.FilterState
import biz.ohrae.challenge_screen.model.user.UserChallengeListState
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeActivity.Companion.REWARD
import biz.ohrae.challenge_screen.util.OnBottomReached
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import timber.log.Timber

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
    rewardFilter: RewardFilter = RewardFilter.mock(),
    onBottomReached: () -> Unit = {},
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DefaultWhite)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = {
                    onRefresh()
                }
            ) {
                Reward(user, select, clickListener, rewardList, rewardFilter, onBottomReached)
            }
        }
    }
}

@Composable
private fun RewardsHeader(
    user: User? = null,
    clickListener: MyChallengeClickListener? = null,
    rewardFilter: RewardFilter
) {
    val expireRewards by remember {
        mutableStateOf(user?.monthly_expire_rewards_amount ?: 0)
    }

    val availableRewards by remember {
        mutableStateOf(user?.rewards_amount ?: 0)
    }
    Column(
    ) {
        Column(
            modifier = Modifier.padding(24.dp, 0.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
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
                    text = "${Utils.numberToString(expireRewards.toString())}원",
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
            Spacer(modifier = Modifier.height(18.dp))
        }

        RewardFilterCard(clickListener, rewardFilter, rewardFilter.selectRewardFilter)
    }
}


@Composable
fun RewardFilterCard(
    clickListener: MyChallengeClickListener?,
    rewardFilter: RewardFilter,
    selectFilter: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp, 0.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(rewardFilter?.rewardFilter!!) { item ->
                    PaidFilterCard(modifier = Modifier,
                        text = item.name,
                        select = item.name_en == selectFilter,
                        onClick = { clickListener?.onClickRewardFilterType(item.name_en) })
                }

                item {
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }
    }
}


@Composable
fun Reward(
    user: User? = null,
    select: Boolean = true,
    clickListener: MyChallengeClickListener? = null,
    rewardList: List<RewardData>? = null,
    rewardFilter: RewardFilter,
    onBottomReached: () -> Unit = {},
) {

    val availableRewards by remember {
        mutableStateOf(user?.rewards_amount ?: 0)
    }
    val enabled = availableRewards >= 5000
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                RewardsHeader(
                    user = user,
                    clickListener = clickListener,
                    rewardFilter = rewardFilter
                )
            }
            if (rewardList.isNullOrEmpty()) {
                item {
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
                }
            } else {
                items(rewardList) { item ->
                    val percent = item.inChallenge?.achievement_percent ?: ""
                    val title = item.challenge?.goal ?: ""
                    RewardHistoryItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        date = Utils.convertDate8(item?.created_date.toString()),
                        progress = percent,
                        title = title,
                        price = item.amount.toString(),
                        progressStatus = Utils.reward(item.type),
                        background = Utils.rewardBackground(item.type),
                        textColor = Utils.rewardTextColor(item.type),
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "출금신청",
            enabled = enabled,
            onClick = {
                clickListener?.onClickApplyWithdraw()
            }
        )
    }
    listState.OnBottomReached {
        Timber.e("bottom reached!!")
        onBottomReached()
    }
}