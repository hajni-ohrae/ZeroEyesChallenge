package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.card.ChallengeItemData
import biz.ohrae.challenge.ui.components.avatar.Avatar
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel
import biz.ohrae.challenge.ui.theme.*

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ChallengeCardItemGallery() {
    val list = listOf(
        ChallengeItemData("매일 6시간씩 한국사 공부", "하진", "오늘부터 시작", "4주동안", "주말만", 17, "4월11일(월)", "4월24일(일)"),
        ChallengeItemData("7월 한달 | 완벽한 스마트 학습 25회 달성하기", "하진", "오늘부터 시작", "4주동안", "주말만", 17, "4월11일(월)", "4월24일(일)"),
        ChallengeItemData("매일 6시간씩 한국사 공부", "하진", "오늘부터 시작", "4주동안", "주말만", 17, "4월11일(월)", "4월24일(일)"),
        ChallengeItemData("매일 6시간씩 한국사 공부", "하진", "오늘부터 시작", "4주동안", "주말만", 17, "4월11일(월)", "4월24일(일)")
    )
    Column(modifier = Modifier.background(GrayColor5)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { item ->
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
fun ChallengeCardItem(
    title: String,
    userName: String,
    dDay: String,
    week: String,
    numberOfTime: String,
    personnel: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row() {
                CategorySurFace(
                    modifier = Modifier,
                    text = "무료",
                    backgroundColor = Color(0x335c94ff),
                    textColor = Color(0xff5c94ff)
                )
                Spacer(modifier = Modifier.width(5.dp))
                CategorySurFace(
                    modifier = Modifier,
                    text = "사진인증",
                    backgroundColor = Color(0xffdedede),
                    textColor = Color(0xff7c7c7c)
                )
                Spacer(modifier = Modifier.width(5.dp))
                CategorySurFace(
                    modifier = Modifier,
                    text = "18세미만 참여불가",
                    backgroundColor = Color(0x33c27247),
                    textColor = Color(0xffc27247)
                )
            }
            Text(
                modifier = Modifier.padding(0.dp, 12.dp), text = title,
                fontSize = dpToSp(dp = 16.dp),
            )
            ChallengeDurationLabel(
                modifier = Modifier.fillMaxWidth(),
                dDay = dDay, week = week, numberOfTimes = numberOfTime
            )
            Spacer(modifier = Modifier.height(19.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1875f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row() {
                    circularAvatar(
                        modifier = Modifier
                            .fillMaxHeight()
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = userName,
                        textAlign = TextAlign.Center,
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 12.dp)
                    )
                }
                Row() {
                    Avatar()
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = personnel.toString(),
                        textAlign = TextAlign.Center,
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 12.dp)
                    )
                }

            }

        }
    }

}