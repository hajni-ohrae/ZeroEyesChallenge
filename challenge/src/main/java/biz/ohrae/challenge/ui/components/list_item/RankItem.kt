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
import biz.ohrae.challenge.ui.components.avatar.AvatarWithNumber
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.theme.TextBlack
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
            RankItem(rank.rank, rank.userName, rank.timeDays, rank.progress, "10회", isMe = true)
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
    userName: String = "",
    timeDays: String = "",
    progress: String = "",
    count: String = "",
    isMe: Boolean = false,
    profileImage: String? = null,
) {
    val name = if (isMe) {
        "$userName(나)"
    } else {
        userName
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (rank) {
                "1" -> {
                    AvatarWithNumber(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        number = rank,
                        url = profileImage.toString(),
                    )
                }
                "2" -> {
                    AvatarWithNumber(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        number = rank,
                        url = profileImage.toString(),
                        borderColor = Color(0xff6fcf97),
                        numberBackgroundColor = Color(0xff1aaf5a)
                    )
                }
                "3" -> {
                    AvatarWithNumber(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        number = rank,
                        url = profileImage.toString(),
                        borderColor = Color(0xfff2994a),
                        numberBackgroundColor = Color(0xffeb5757)
                    )
                }
                else -> {
                    circularAvatar(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        url = profileImage.toString()
                    )
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.051f))
            Text(
                text = rank,
                style = myTypography.extraBold,
                fontSize = dpToSp(dp = 14.dp),
                color = TextBlack
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.051f))
            Text(
                text = name,
                style = myTypography.default,
                fontSize = dpToSp(dp = 14.dp),
                color = TextBlack
            )
            Spacer(modifier = Modifier.weight(1f))
            if (timeDays.isNotEmpty()) {
                Text(
                    text = timeDays,
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = TextBlack
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.051f))
            }
            if (count.isNotEmpty()) {
                Text(
                    text = count,
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = TextBlack
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.051f))
            }
            if (progress.isNotEmpty()) {
                Text(
                    text = progress,
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff005bad)
                )
            }
        }
    }
}