package biz.ohrae.challenge.ui.components.list_item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.list_item.RankItemData
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun RankItemGallery() {
    val list = listOf(
        RankItemData("", "1","이오래", "30시간 10분", "100%"),
        RankItemData("", "2","김오래", "30시간 10분", "100%"),
        RankItemData("", "3","최오래", "30시간 10분", "100%"),
        RankItemData("", "4","박오래", "30시간 10분", "100%"),
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(list) { rank ->
            RankItem(rank.rank, rank.userName, rank.timeDays, rank.progress)
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun RankItem(
    rank: String = "1",
    userName: String = "이오래",
    timeDays: String = "30시간 10분",
    progress: String = "100%",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(7.2f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.866f)
                .fillMaxHeight(0.72f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            circularAvatar(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.051f))
            Text(
                text = rank,
                style = myTypography.extraBold,
                fontSize = dpToSp(dp = 14.dp),
                color = DefaultBlack
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.051f))
            Text(
                text = userName,
                style = myTypography.default,
                fontSize = dpToSp(dp = 14.dp),
                color = DefaultBlack
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = timeDays,
                style = myTypography.extraBold,
                fontSize = dpToSp(dp = 14.dp),
                color = DefaultBlack
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.051f))
            Text(
                text = progress,
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff005bad)
            )
        }
    }
}