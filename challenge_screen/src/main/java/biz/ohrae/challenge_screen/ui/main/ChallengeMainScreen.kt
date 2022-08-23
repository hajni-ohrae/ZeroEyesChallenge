package biz.ohrae.challenge_screen.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.list_item.ChallengeItemData
import biz.ohrae.challenge.ui.components.card.PaidFilterCard

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ChallengeMainScreen(select:Boolean = true,challengeItemData: ChallengeItemData = ChallengeItemData.mock()) {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row() {
            PaidFilterCard(modifier = Modifier, text = "전체", select = select)
            Spacer(modifier = Modifier.width(4.dp))
            PaidFilterCard(modifier = Modifier, text = "유료")
            Spacer(modifier = Modifier.width(4.dp))
            PaidFilterCard(modifier = Modifier, text = "무료")
            PaidFilterCard(modifier = Modifier, text = "무료")
        }
        LazyVerticalGrid(
            modifier = Modifier.padding(24.dp, 0.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            items(list) { item ->
//                CertificationImageItem(item.imageUrl, item.userName, item.count, item.date)
//            }
        }
    }
}