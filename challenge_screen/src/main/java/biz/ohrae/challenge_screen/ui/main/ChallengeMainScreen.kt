package biz.ohrae.challenge_screen.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.MainScreenState
import biz.ohrae.challenge.ui.components.card.ChallengeCardItem
import biz.ohrae.challenge.ui.components.card.MainTopCard
import biz.ohrae.challenge.ui.components.card.PaidFilterCard

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun ChallengeMainScreen(
    select: Boolean = true,
    mainScreenState: MainScreenState? = null,
    clickListener: MainClickListener? = null
) {
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box() {
            LazyColumn(
                modifier = Modifier
                    .padding(24.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    ItemHeader(
                        mainScreenState = mainScreenState,
                    )
                }
                items(mainScreenState?.challengeList!!) { item ->
                    ChallengeCardItem(
                        item.goal!!,
                        null,
                        item.start_date!!,
                        item.period.toString(),
                        item.verification_period_type!!,
                        null,
                        onClick = {
                            clickListener?.onClickChallengeItem(it)
                        }
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { clickListener?.onClickRegister() }) {
                Surface(modifier = Modifier.size(60.dp), color = Color(0xff005bad),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    
                }
            }
        }

    }
}

@Composable
fun ItemHeader(
    select: Boolean = true,
    mainScreenState: MainScreenState? = null,
) {

    Column {
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            items(mainScreenState!!.topBannerList) { item ->
//                MainTopCard(
//                    content = item.content
//                )
//            }
        }
        FilterCard(select = select)
    }
}

@Composable
fun FilterCard(select: Boolean = true) {
    Row(modifier = Modifier.padding(0.dp, 22.dp)) {
        PaidFilterCard(modifier = Modifier, text = "전체", select = select)
        Spacer(modifier = Modifier.width(4.dp))
        PaidFilterCard(modifier = Modifier, text = "유료")
        Spacer(modifier = Modifier.width(4.dp))
        PaidFilterCard(modifier = Modifier, text = "무료")
        PaidFilterCard(modifier = Modifier, text = "무료")
    }
}

