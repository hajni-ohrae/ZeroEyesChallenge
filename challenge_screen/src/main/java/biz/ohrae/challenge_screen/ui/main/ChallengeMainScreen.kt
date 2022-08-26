package biz.ohrae.challenge_screen.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.card.ChallengeData
import biz.ohrae.challenge.ui.components.card.ChallengeCardItem
import biz.ohrae.challenge.ui.components.card.PaidFilterCard

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ChallengeMainScreen(
    select: Boolean = true,
    challengeData: ChallengeData? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp, 0.dp),
    ) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                ItemHeader(
                    challengeData = challengeData!!,
                    select = select
                )
            }
            items(challengeData?.challengeList!!) { item ->
                ChallengeCardItem(
                    item.title,
                    item.userName,
                    item.dDay,
                    item.week,
                    item.numberOfTimes,
                    item.personnel
                )
            }
        }
    }
}

@Composable
fun ItemHeader(
    select: Boolean = true,
    challengeData: ChallengeData
) {
    Column {
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(challengeData.challengeList) { item ->
                ChallengeCardItem(
                    item.title,
                    item.userName,
                    item.dDay,
                    item.week,
                    item.numberOfTimes,
                    item.personnel
                )
            }
        }
        FilterCard(select = select)
    }
}

@Composable
fun FilterCard(select: Boolean = true) {
    Row() {
        PaidFilterCard(modifier = Modifier, text = "전체", select = select)
        Spacer(modifier = Modifier.width(4.dp))
        PaidFilterCard(modifier = Modifier, text = "유료")
        Spacer(modifier = Modifier.width(4.dp))
        PaidFilterCard(modifier = Modifier, text = "무료")
        PaidFilterCard(modifier = Modifier, text = "무료")
    }
}

