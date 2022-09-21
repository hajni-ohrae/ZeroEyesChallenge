package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.MainScreenState
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge.ui.components.card.ChallengeCardItem
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.ui.main.ItemHeader


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun SavedChallengeScreen(
    mainScreenState: MainScreenState? = MainScreenState.mock()
) {
    Column() {
        LazyColumn(
            modifier = Modifier
                .padding(24.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(mainScreenState?.challengeList!!) { item ->
                ChallengeCardItem(
                    item.title,
                    item.userName,
                    item.dDay,
                    item.week,
                    item.numberOfTimes,
                    item.personnel,
                    onClick = {
//                        clickListener?.onClickChallengeItem(it)
                    }
                )
            }
        }
    }
}