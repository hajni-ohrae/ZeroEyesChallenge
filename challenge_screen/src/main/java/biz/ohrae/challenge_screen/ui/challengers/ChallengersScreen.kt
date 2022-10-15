package biz.ohrae.challenge_screen.ui.challengers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import biz.ohrae.challenge.ui.components.list_item.RankItem
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_repo.model.user.User

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengersScreen(
    challengers: List<User>? = null
) {
    if (challengers == null) {
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize().background(DefaultWhite)) {
        items(challengers) { item ->
            RankItem(
                userName = item.getUserName(),
                rank = item.inChallenge?.get(0)?.ranking.toString()
            )
        }
    }
}