package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.banner.FlatBanner
import biz.ohrae.challenge.ui.components.button.ArrowTextButton
import biz.ohrae.challenge.ui.components.list_item.RedCardHistoryItem
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.user.RedCardListState
import biz.ohrae.challenge_repo.model.user.RedCardState
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.util.OnBottomReached
import timber.log.Timber


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun RedCardScreen(
    clickListener: MyChallengeClickListener? = null,
    redCardListState: RedCardListState? = null,
    onBottomReached: () -> Unit = {},
) {
    Column(modifier = Modifier.background(DefaultWhite)) {
        Column(modifier = Modifier.padding(24.dp, 0.dp)) {
            Text(text = "레드카드", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "레드카드 3회 이상 발급 시 서비스 이용이 영구 제한될 수 있습니다\n하단의 레드카드 정책을 확인 해주세요",
                style = myTypography.w500,
                fontSize = dpToSp(dp = 12.dp),
                color = Color(0xff828282),
                lineHeight = dpToSp(dp = 17.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ArrowTextButton(
                text = "레드카드 정책 보러가기",
                onClick = { clickListener?.onClickPolicy(MyChallengeActivity.RED_CARD) }

            )
            Spacer(modifier = Modifier.height(16.dp))

            FlatBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(49.dp),
                backgroundColor = Color(0xfffbefef),
                title = "레드카드",
                titleColor = Color(0xff6c6c6c),
                content = redCardListState?.totalRedCard.toString(),
                contentColor = Color(0xffff0000)
            )
            Spacer(modifier = Modifier.height(21.dp))

        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color(0xfffafafa))
        ) {
            RedCardList(onBottomReached = onBottomReached, redCardListState)
        }
    }
}

@Composable
fun RedCardList(
    onBottomReached: () -> Unit = {},
    redCardListState: RedCardListState? = null,
) {
    val listState = rememberLazyListState()

    if (redCardListState?.redCardState != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 0.dp),
        ) {
            items(redCardListState?.redCardState!!) { item ->
                RedCardHistoryItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 99.dp),
                    date = Utils.convertDate8(item?.created_date.toString())?: "",
                    title = item.reason ?: "",
                    content = item.canceled_reason ?: ""
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xfffafafa))
                )
            }
            item {
                listState.OnBottomReached {
                    Timber.e("bottom reached!!")
                    onBottomReached()
                }
            }
        }
    } else {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 0.dp),
            text = "내역이 없습니다.",
            style = myTypography.w500,
            fontSize = dpToSp(dp = 20.dp),
            color = Color(0xff828282)
        )
    }

}