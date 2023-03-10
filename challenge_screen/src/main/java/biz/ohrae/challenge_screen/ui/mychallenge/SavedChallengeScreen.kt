package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import biz.ohrae.challenge.ui.components.card.ChallengeCardItem
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeVerificationPeriodMap
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.main.MainScreenState
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
fun SavedChallengeScreen(
    mainScreenState: MainScreenState? = MainScreenState.mock(),
    clickListener: MyChallengeClickListener? = null,
    onBottomReached: () -> Unit = {},
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xfff9f9f9))) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                onRefresh()
            }
        ) {
            SavedChallengeList(
                mainScreenState = mainScreenState,
                clickListener = clickListener,
                onBottomReached = onBottomReached
            )
        }
    }
}

@Composable
fun SavedChallengeList(
    mainScreenState: MainScreenState?,
    clickListener: MyChallengeClickListener? = null,
    onBottomReached: () -> Unit = {},
) {
    val listState = rememberLazyListState()

    if (!mainScreenState?.challengeList.isNullOrEmpty()) {
        LazyColumn(
            modifier = Modifier
                .padding(24.dp, 17.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(
                mainScreenState?.challengeList!!,
                key = { index, _ -> "key-$index" }) { index, item ->
                val startDay = Utils.getRemainTimeDays(item.start_date.toString())
                val type = challengeVerificationPeriodMap[item.verification_period_type]
                val weekType = if (type.isNullOrEmpty()) "???${item.per_week}???" else type
                val ageType = Utils.getAgeType(item.age_limit_type.toString())
                val nickNameColor =
                    if (item.owner?.main_color != null) Color(item.owner?.main_color!!.toColorInt()) else DefaultBlack
                ChallengeCardItem(
                    index,
                    item.id,
                    item.goal.toString(),
                    item.owner?.getUserName(),
                    startDay.toString(),
                    item.period.toString(),
                    weekType.toString(),
                    item.summary?.total_user_cnt,
                    imagePath = item.owner?.imageFile?.thumbnail_path ?: "",
                    onClick = {
                        Timber.e("chall id : ${item.id}")
                        clickListener?.onClickChallengeItem(it)
                    },
                    nickNameColor = nickNameColor,
                    isFree = item.min_deposit_amount == 0,
                    ageType = ageType,
                    isPhoto = item.is_verification_photo == 1,
                    isTime = item.is_verification_time == 1,
                    isCheckIn = item.is_verification_checkin == 1,
                )
            }
        }
    } else {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            text = "????????? ???????????? ????????????.",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 20.dp),
            color = Color(0xff828282)
        )
    }

    listState.OnBottomReached {
        Timber.e("bottom reached!!")
        onBottomReached()
    }
}