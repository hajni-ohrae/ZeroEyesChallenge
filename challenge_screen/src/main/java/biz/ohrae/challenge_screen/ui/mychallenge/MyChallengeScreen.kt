package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.components.card.ChallengesInParticipationCard
import biz.ohrae.challenge.ui.components.card.MyChallengeIngoBox
import biz.ohrae.challenge.ui.components.filter.PaidFilterCard
import biz.ohrae.challenge.ui.components.menu.MenuItem
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeDetailStatusMap
import biz.ohrae.challenge_component.R
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.main.FilterState
import biz.ohrae.challenge_screen.model.user.UserChallengeListState
import biz.ohrae.challenge_screen.ui.main.MainClickListener
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
fun MyChallengeScreen(
    user: User? = null,
    clickListener: MyChallengeClickListener? = null,
    userChallengeListState: UserChallengeListState? = null,
    filterState: FilterState = FilterState.mock(),
    onBottomReached: () -> Unit = {},
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    if (user == null) {
        return
    }

    Column(
        modifier = Modifier
            .padding(24.dp, 0.dp)
            .fillMaxWidth()
            .fillMaxSize()
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
                if (userChallengeListState != null) {
                    UserChallengeList(
                        user = user,
                        clickListener = clickListener,
                        userChallengeListState = userChallengeListState,
                        filterState = filterState,
                        onBottomReached = onBottomReached
                    )
                }
            }

        }
    }
}

@Composable
fun ItemHeader(
    user: User? = null,
    availableRewards: String,
    clickListener: MyChallengeClickListener? = null,
    filterState: FilterState = FilterState.mock(),
    onClickProfile: () -> Unit = {}
) {

    Column(Modifier.fillMaxWidth()) {

        Column() {
            Row(
                modifier = Modifier.clickable {
                    onClickProfile()
                }
            ) {
                circularAvatar(
                    modifier = Modifier.size(50.dp),
                    url = user?.imageFile?.path.toString(),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Text(
                        text = user?.getUserName() ?: "이름",
                        style = myTypography.w700,
                        fontSize = dpToSp(dp = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = user?.phone_number.toString(),
                        style = myTypography.w700,
                        fontSize = dpToSp(dp = 12.dp),
                        color = Color(0xff4f4f4f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        MyChallengeIngoBox(
            user?.summary?.attend_cnt.toString(),
            user?.summary?.achievement_cnt.toString(),
            user?.summary?.achievement_percent.toString()
        )
        Spacer(modifier = Modifier.height(8.dp))
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_coin,
            title = "보유 리워즈",
            description = "${Utils.numberToString(availableRewards.toString())}원",
            onClick = { clickListener?.onClickReward() }
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_card,
            title = "결제내역",
            onClick = { clickListener?.onClickPaymentDetail() }
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_like,
            title = "저장한 챌린지",
            onClick = { clickListener?.onClickSavedChallenge() }
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_note,
            title = "레드카드",
            onClick = { clickListener?.onClickRedCard() }
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "참여한 챌린지",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        UserFilterCard(clickListener = clickListener, filterState, filterState.selectUserChallengeType)
    }
}

@Composable
fun UserChallengeList(
    user: User? = null,
    clickListener: MyChallengeClickListener? = null,
    userChallengeListState: UserChallengeListState? = null,
    filterState: FilterState = FilterState.mock(),
    onBottomReached: () -> Unit = {},
    ) {

    val listState = rememberLazyListState()

    val availableRewards by remember {
        mutableStateOf(user?.rewards_amount ?: 0)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            ItemHeader(
                user = user,
                availableRewards = availableRewards.toString(),
                clickListener = clickListener,
                filterState = filterState,
                onClickProfile = {
                    clickListener?.onClickProfile()
                }
            )
        }
        if (userChallengeListState != null) {
            items(userChallengeListState?.userChallengeList!!) { item ->
                val inChallenge = item.inChallenge?.get(0)
                val buttonName = Utils.getAuthButtonName(item,true)
                ChallengesInParticipationCard(
                    modifier = Modifier.fillMaxWidth(),
                    title = item.goal.toString(),
                    count = inChallenge?.verification_cnt.toString(),
                    total = inChallenge?.total_verification_cnt.toString(),
                    progressStatus = challengeDetailStatusMap[item.status]?.status.toString(),
                    achievementRate = inChallenge?.achievement_percent.toString(),
                    Utils.userChallengeBackground(item.status),
                    Utils.userChallengeTextColor(item.status),
                    startDay = item.start_date.toString(),
                    status = item.status.toString(),
                    buttonName = buttonName,
                    onClick = {
                        clickListener?.onClickChallengeAuthItem(item.id,item.is_verification_photo)
                    },
                    onCardClick = { clickListener?.onClickMyChallengeCard(item.id) },
                    buttonTextColor = Utils.getAuthBtnTextColor(item),
                    buttonColor = Utils.getAuthBtnColor(item)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    listState.OnBottomReached {
        Timber.e("bottom reached!!")
        onBottomReached()
    }
}

@Composable
fun UserFilterCard(
    clickListener: MyChallengeClickListener?,
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
                items(filterState?.userChallengeFilter!!) { item ->
                    PaidFilterCard(modifier = Modifier,
                        text = item.name,
                        select = item.name_en == selectFilter,
                        onClick = { clickListener?.onClickFilterType(item.name_en) })
                }
            }
        }
    }
}
