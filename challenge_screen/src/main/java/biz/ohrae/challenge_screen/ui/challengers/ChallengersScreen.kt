package biz.ohrae.challenge_screen.ui.challengers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.list_item.RankItem
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengersScreen(
    challengeData: ChallengeData? = null,
    challengers: List<User>? = null,
    type: String? = "",
    authType: String? = ""
) {
    if (challengers == null) {
        return
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultWhite)
    ) {
        if (type == "finish") {
            items(challengers) { item ->
                val timeDays = when (authType) {
                    "photo" -> "${item.inChallenge?.get(0)?.verification_cnt.toString()}회"
                    "checkin" -> "${item.inChallenge?.get(0)?.verification_cnt.toString()}일"
                    else -> "${item.inChallenge?.get(0)?.verification_time.toString()}"
                }
                RankItem(
                    userName = item.getUserName(),
                    rank = item.inChallenge?.get(0)?.ranking.toString(),
                    timeDays = timeDays,
                    progress = "${item.inChallenge?.get(0)?.achievement_percent.toString()}%",
                    profileImage = item.imageFile?.path
                )
            }
        } else {
            items(challengers) { item ->
                RankItem(
                    userName = item.getUserName(),
                    rank = item.inChallenge?.get(0)?.ranking.toString(),
                    profileImage = item.imageFile?.path
                )
            }
        }
    }
}