package biz.ohrae.challenge_screen.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.card.CertificationImageItem
import biz.ohrae.challenge.ui.components.card.ChallengeCardItem
import biz.ohrae.challenge.ui.components.filter.FeedFilter
import biz.ohrae.challenge.ui.components.filter.FeedItem
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeVerificationPeriodMap
import biz.ohrae.challenge_repo.model.verify.VerifyData
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.main.MainScreenState
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeClickListener
import biz.ohrae.challenge_screen.util.OnBottomReached
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import timber.log.Timber


@OptIn(ExperimentalPagerApi::class)
@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1800
)
@Composable
fun ChallengeAuthFeedScreen(
    challengeVerifiedList: List<VerifyData>? = null,
    clickListener: ChallengeAuthFeedClickListener? = null
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
            VerifiedList(challengeVerifiedList, clickListener)
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
        ) {
            items(challengeVerifiedList) { item ->
                FeedItem(
                    imageUrl = item.imageFile?.path.toString(),
                    username = item.user?.getUserName().toString(),
                    date = Utils.convertDate(item.updated_date),
                    count = item.cnt,
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
                            item.id,like)
                    },
                    isLike = item.is_like
                )
            }
        }

    }

    listState.OnBottomReached {
        Timber.e("bottom reached!!")
        onBottomReached()
    }

}