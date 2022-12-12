package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.filter.FeedFilter
import biz.ohrae.challenge.ui.components.filter.FeedItem
import biz.ohrae.challenge_repo.model.verify.VerifyData
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.util.OnBottomReached
import timber.log.Timber


@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1800
)
@Composable
fun ChallengeAuthFeedScreen(
    challengeVerifiedList: List<VerifyData>? = null,
    clickListener: ChallengeAuthFeedClickListener? = null,
    onBottomReached: () -> Unit = {},
) {
    var isMine by remember { mutableStateOf(false) }
    var isOrder by remember { mutableStateOf(false) }
    Column(modifier = Modifier.background(Color(0xfff7f7f7))) {
        FeedFilter(
            onOrder = {
                isOrder = !isOrder
                clickListener?.onClickOrder(isOrder)
            },
            onMine = {
                isMine = !isMine
                clickListener?.onClickMine(isMine)
            },
            isMine = isMine,
            isOrder = isOrder,
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            VerifiedList(
                challengeVerifiedList = challengeVerifiedList,
                clickListener = clickListener,
                onBottomReached = onBottomReached
            )
        }
    }
}

@Composable
fun VerifiedList(
    challengeVerifiedList: List<VerifyData>? = null,
    clickListener: ChallengeAuthFeedClickListener? = null,
    onBottomReached: () -> Unit = {},
) {
    val listState = rememberLazyListState()
    var like by remember { mutableStateOf(false) }

    if (challengeVerifiedList != null) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = listState,
        ) {
            items(challengeVerifiedList) { item ->
                val cnt = if (item.type == "staying_time") item.staying_time_cnt else item.cnt
                val time =
                    when (item.type) {
                        "staying_time" -> Utils.convertDate2(item.verified_date)
                        "checkin" -> Utils.convertDate9(item.checkin_date, true)
                        else -> Utils.convertDate9(item.created_date, true)
                    }

                FeedItem(
                    imageUrl = item.imageFile?.path.toString(),
                    avatarUrl = item.user?.imageFile?.thumbnail_path ?: "",
                    username = item.id + " " + item.user?.getUserName().toString(),
                    date = time,
                    count = cnt,
                    comment = item.comment,
                    type = item.type,
                    onReport = {
                        item.user?.let {
                            clickListener?.onClickReport(
                                item.id,
                                it
                            )
                        }
                    },
                    onLike = {
                        like = !like
                        clickListener?.onClickLike(
                            item.id, like
                        )
                    },
                    isLike = item.is_like,
                    stayingTime = item.staying_time,
                )
            }
        }
    }
    listState.OnBottomReached {
        Timber.e("bottom reached!!")
        onBottomReached()
    }
}