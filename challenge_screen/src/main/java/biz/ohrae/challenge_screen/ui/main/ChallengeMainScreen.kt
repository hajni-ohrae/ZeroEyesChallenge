package biz.ohrae.challenge_screen.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.card.ChallengeCardItem
import biz.ohrae.challenge.ui.components.card.ChallengesInParticipationCard
import biz.ohrae.challenge.ui.components.card.MainTopCard
import biz.ohrae.challenge.ui.components.card.PaidFilterCard
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeDetailStatusMap
import biz.ohrae.challenge.util.challengeVerificationPeriodMap
import biz.ohrae.challenge_component.R
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_repo.util.prefs.Utils.getAuthType
import biz.ohrae.challenge_repo.util.prefs.Utils.getOpenType
import biz.ohrae.challenge_screen.model.main.FilterState
import biz.ohrae.challenge_screen.model.main.MainScreenState
import biz.ohrae.challenge_screen.model.user.UserChallengeListState
import biz.ohrae.challenge_screen.util.OnBottomReached
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import timber.log.Timber

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ChallengeMainScreen(
    mainScreenState: MainScreenState? = null,
    clickListener: MainClickListener? = null,
    filterState: FilterState = FilterState.mock(),
    userChallengeListState: UserChallengeListState? = null,
    isRefreshing: Boolean = false,
    onBottomReached: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(),
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
                ChallengeList(
                    mainScreenState = mainScreenState,
                    clickListener = clickListener,
                    filterState = filterState,
                    userChallengeListState = userChallengeListState,
                    onBottomReached = onBottomReached
                )
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { clickListener?.onClickRegister() }) {
                Surface(
                    modifier = Modifier.size(60.dp), color = Color(0xff005bad),
                    shape = RoundedCornerShape(40.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(14.dp),
                        painter = painterResource(id = R.drawable.icon_write),
                        contentDescription = "icon",
                        tint = DefaultWhite,
                    )
                }
            }
        }
    }
}

@Composable
private fun ChallengeList(
    mainScreenState: MainScreenState? = null,
    clickListener: MainClickListener? = null,
    filterState: FilterState = FilterState.mock(),
    userChallengeListState: UserChallengeListState? = null,
    onBottomReached: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .padding(24.dp, 0.dp)
            .fillMaxWidth()
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState,
    ) {
        item {
            ItemHeader(
                mainScreenState = mainScreenState,
                clickListener = clickListener,
                filterState = filterState,
                userChallengeListState = userChallengeListState
            )
        }
        Timber.e("list size ${mainScreenState?.challengeList!!.size}")
        items(mainScreenState?.challengeList!!, key = { item -> item.id }) { item ->
            val startDay = Utils.getRemainTimeDays(item.start_date.toString())
            val type = challengeVerificationPeriodMap[item.verification_period_type]
            ChallengeCardItem(
                item.id,
                item.goal.toString(),
                item.user?.getUserName(),
                startDay.toString(),
                item.period.toString(),
                type.toString(),
                item.summary?.total_user_cnt,
                getAuthType(item),
                getOpenType(item),
                item.is_adult_only,
                onClick = {
                    Timber.e("chall id : ${item.id}")
                    clickListener?.onClickChallengeItem(it)
                },
            )
        }
    }
    listState.OnBottomReached {
        Timber.e("bottom reached!!")
        onBottomReached()
    }
}

@Composable
fun ItemHeader(
    mainScreenState: MainScreenState? = null,
    clickListener: MainClickListener?,
    filterState: FilterState = FilterState.mock(),
    userChallengeListState: UserChallengeListState? = null
) {
    Column(Modifier.fillMaxWidth()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mainScreenState?.topBannerList!!) { item ->
                MainTopCard(
                    modifier = Modifier.fillParentMaxSize(),
                    content = item.content
                )
            }
        }
        if (userChallengeListState != null && !userChallengeListState.userChallengeList.isNullOrEmpty()) {
            InChallenges(
                clickListener = clickListener,
                userChallengeList = userChallengeListState.userChallengeList
            )
        }
        FilterCard(clickListener = clickListener, filterState, filterState.selectFilterType)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun InChallenges(
    userChallengeList: List<ChallengeData>,
    clickListener: MainClickListener?,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val pagerState = rememberPagerState()

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "참여중인 챌린지",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(10.dp))
        Timber.e("userChallengeList size :${userChallengeList.size}")
        HorizontalPager(
            count = userChallengeList.size,
            state = pagerState
        ) {
            val item = userChallengeList[currentPage]
            val inChallenge = item.inChallenge?.get(0)
            ChallengesInParticipationCard(
                modifier = Modifier.fillMaxWidth(),
                title = item.goal.toString(),
                count = inChallenge?.today_verified_cnt.toString(),
                maxPeople = inChallenge?.verified_cnt.toString(),
                progressStatus = item.status,
                achievementRate = inChallenge?.achievement_percent.toString(),
                Utils.userChallengeBackground(item.status),
                Utils.userChallengeTextColor(item.status),
                onClick = { clickListener?.onClickChallengeAuthItem(item.id) }
            )
        }
    }
}

@Composable
fun FilterCard(
    clickListener: MainClickListener?,
    filterState: FilterState = FilterState.mock(),
    selectFilter: String
) {
    Row(
        modifier = Modifier
            .padding(0.dp, 22.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(filterState?.filterItem!!) { item ->
                    PaidFilterCard(modifier = Modifier,
                        text = item.name,
                        select = item.name_en == selectFilter,
                        onClick = { clickListener?.onClickFilterType(item.name_en) })
                }
            }
        }
        PaidFilterCard(modifier = Modifier, icon = R.drawable.icon_candle_2,
            onClick = { clickListener?.onClickFilterType("filter") })
    }
}




