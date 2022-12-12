package biz.ohrae.challenge_screen.ui.challengers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.list_item.RankItem
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_screen.util.OnBottomReached
import timber.log.Timber

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengersScreen(
    challengeData: ChallengeData? = null,
    challengers: List<User>? = null,
    userId: String? = null,
    type: String? = "",
    authType: String? = "",
    onBottomReached: () -> Unit = {},
) {
    if (challengers == null) {
        return
    }
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultWhite)
            .padding(24.dp, 0.dp),
        state = listState
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        if (type == "finish") {
            items(challengers) { item ->
                val timeDays = when (authType) {
                    "photo" -> "${item.inChallenge?.get(0)?.verification_cnt.toString()}회"
                    "checkin" -> "${item.inChallenge?.get(0)?.verification_cnt.toString()}일"
                    else -> item.inChallenge?.get(0)?.verification_time.toString()
                }
                RankItem(
                    userName = item.getUserName(),
                    rank = item.inChallenge?.get(0)?.ranking.toString(),
                    timeDays = timeDays,
                    progress = "${item.inChallenge?.get(0)?.achievement_percent.toString()}%",
                    profileImage = item.imageFile?.path,
                    isMe = userId == item.id
                )
            }
        } else {
            items(challengers) { item ->
                val count = if (authType == "photo") {
                    "${item.inChallenge?.get(0)?.verification_cnt.toString()}회"
                } else if (authType == "checkin") {
                    "${item.inChallenge?.get(0)?.verification_cnt.toString()}일"
                } else {
                    ""
                }

                val timeDays = if (authType == "staying_time") {
                    "${item.inChallenge?.get(0)?.verification_time.toString()}회"
                } else {
                    ""
                }

                RankItem(
                    userName = item.getUserName(),
                    rank = item.inChallenge?.get(0)?.ranking.toString(),
                    profileImage = item.imageFile?.path,
                    isMe = userId == item.id,
                    count = count,
                    timeDays = timeDays
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    listState.OnBottomReached {
        Timber.e("call onBottomReached!!")
        onBottomReached()
    }
}