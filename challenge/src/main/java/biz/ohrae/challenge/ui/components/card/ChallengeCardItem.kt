package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.list_item.ChallengeItemData
import biz.ohrae.challenge.model.state.ChallengeDetailStatus
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.components.label.ChallengeDurationLabel
import biz.ohrae.challenge.ui.theme.*
import biz.ohrae.challenge_component.BuildConfig
import biz.ohrae.challenge_component.R

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ChallengeCardItemGallery() {
    val list = listOf(
        ChallengeItemData(
            id = "1",
            "매일 6시간씩 한국사 공부",
            "하진",
            "오늘부터 시작",
            "4주동안",
            "주말만",
            17,
            "4월11일(월)",
            "4월24일(일)",
            state = ChallengeDetailStatus.mock()
        ),
        ChallengeItemData(
            id = "2",
            "7월 한달 | 완벽한 스마트 학습 25회 달성하기",
            "하진",
            "오늘부터 시작",
            "4주동안",
            "주말만",
            17,
            "4월11일(월)",
            "4월24일(일)",
            state = ChallengeDetailStatus.mock()
        ),
        ChallengeItemData(
            id = "3",
            "매일 6시간씩 한국사 공부",
            "하진",
            "오늘부터 시작",
            "4주동안",
            "주말만",
            17,
            "4월11일(월)",
            "4월24일(일)",
            state = ChallengeDetailStatus.mock()
        ),
        ChallengeItemData(
            id = "4",
            "매일 6시간씩 한국사 공부",
            "하진",
            "오늘부터 시작",
            "4주동안",
            "주말만",
            17,
            "4월11일(월)",
            "4월24일(일)",
            state = ChallengeDetailStatus.mock()
        )
    )
    Column(modifier = Modifier.background(GrayColor5)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { item ->
//                ChallengeCardItem(
//                    0,
//                    item.id,
//                    item.title,
//                    item.userName,
//                    item.dDay,
//                    item.week,
//                    item.numberOfTimes,
//                    item.personnel,
//                    onClick = {
//                    }
//                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChallengeCardItem(
    index: Int = 0,
    id: String,
    title: String,
    userName: String?,
    dDay: String,
    week: String,
    numberOfTime: String,
    personnel: Int?,
    onClick: (id: String) -> Unit,
    imagePath: String = "",
    nickNameColor: Color,
    isFree: Boolean,
    ageType: String,
    isPhoto: Boolean,
    isTime: Boolean,
    isCheckIn: Boolean,
) {
    val ageBackgroundColor = if (ageType == "18세 이상 전용") Color(0x33ffadad) else Color(0x33a2cc5e)
    val ageTextColor = if (ageType == "18세 이상 전용") Color(0xffd98181) else Color(0xff73b00e)
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = DefaultWhite,
        elevation = 0.dp,
        onClick = {
            onClick(id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .background(DefaultWhite)
        ) {
            Row {
                if (BuildConfig.DEBUG) {
                    Text(
                        text = index.toString()
                    )
                }
                CategorySurFace(
                    text = if (isFree) "무료" else "유료",
                    backgroundColor = if (isFree) Color(0x33a2cc5e) else Color(0x33ffadad),
                    textColor = if (isFree) Color(0xff73b00e) else Color(0xffd98181)
                )
                if (isPhoto) {
                    Spacer(modifier = Modifier.width(4.dp))
                    CategorySurFace(
                        text = "사진 인증",
                        backgroundColor = Color(0x335c94ff),
                        textColor = Color(0xff5c94ff)
                    )
                }
                if (isTime) {
                    Spacer(modifier = Modifier.width(4.dp))
                    CategorySurFace(
                        text = "이용 시간 인증",
                        backgroundColor = Color(0x33e090d3),
                        textColor = Color(0xffbd6fb0)
                    )
                }
                if (isCheckIn) {
                    Spacer(modifier = Modifier.width(4.dp))
                    CategorySurFace(
                        text = "출석 인증",
                        backgroundColor = Color(0x66f2d785),
                        textColor = Color(0xffe78a00)
                    )
                }
                if (ageType != "제한없음") {
                    Spacer(modifier = Modifier.width(4.dp))
                    CategorySurFace(
                        modifier = Modifier,
                        text = ageType,
                        backgroundColor = ageBackgroundColor,
                        textColor = ageTextColor
                    )

                }
            }
            Text(
                modifier = Modifier.padding(0.dp, 12.dp),
                style = myTypography.default,
                text = title,
                fontSize = dpToSp(dp = 16.dp), color = DefaultBlack,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                        modifier = Modifier.size(20.dp),
                        url = imagePath,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = userName.toString(),
                        textAlign = TextAlign.Center,
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 12.dp),
                        color = nickNameColor
                    )
                }
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.icon_user),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(20.dp),
                        contentDescription = "avatar_fail"
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "${personnel.toString()}명",
                        textAlign = TextAlign.Center,
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 12.dp), color = DefaultBlack
                    )
                }

            }

        }
    }

}