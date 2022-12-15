package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.ChallengeStatusButton
import biz.ohrae.challenge.ui.components.detail.getRemainTime
import biz.ohrae.challenge.ui.components.label.ProgressLabel
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import kotlinx.coroutines.delay

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ChallengesInParticipationCardGallery() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ChallengesInParticipationCard(
            Modifier,
            title = "매일 6시간씩 한국사 공부",
            "1",
            "30",
            "진행중",
            "0.00",
            Color(0x335c94ff),
            Color(0xff5c94ff),
            isPhoto = true,
            isCheckIn = true,
            isTime = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ChallengesInParticipationCard(
            Modifier,
            title = "매일 6시간씩 한국사 공부",
            "1",
            "30",
            "모집중",
            "0.00",
            Color(0xffebfaf1),
            Color(0xff219653),
            isPhoto = true,
            isCheckIn = true,
            isTime = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ChallengesInParticipationCard(
            Modifier,
            title = "매일 6시간씩 한국사 공부",
            "1",
            "30",
            "완료",
            "0.00",
            Color(0xffdedede),
            Color(0xff6c6c6c),
            isPhoto = true,
            isCheckIn = true,
            isTime = true,
        )
    }
}

@Composable
fun ChallengesInParticipationCard(
    modifier: Modifier,
    title: String,
    count: String,
    total: String,
    progressStatus: String,
    achievementRate: String = "0.00",
    background: Color,
    textColor: Color,
    onClick: () -> Unit = {},
    onCardClick: () -> Unit = {},
    buttonName: String = "",
    startDay: String = "",
    status: String = "",
    buttonTextColor: Color = DefaultWhite,
    buttonColor: Color = DefaultWhite,
    todayAuth: String = "",
    isPhoto: Boolean,
    isTime: Boolean,
    isCheckIn: Boolean,
) {
    var remainTime = getRemainTime(startDay)
    var isRemainTime = !remainTime.startsWith("-")
    var timer by remember { mutableStateOf(0) }

    LaunchedEffect(timer) {
        delay(1000 * 60)
        timer++
        remainTime = getRemainTime(startDay)
        isRemainTime = !remainTime.startsWith("-")
    }
    val btnName = if (status == "register") remainTime.replace("-", "") + " 남음" else buttonName
    val btnTextColor = if (achievementRate.toDouble() >= 100f) {
        buttonTextColor
    } else if (status == "register") {
        Color(0xff4985f8)
    } else {
        buttonTextColor
    }
    val btnColor =
        if (achievementRate.toDouble() >= 100f) {
            buttonColor
        } else if (status == "register") {
            DefaultWhite
        } else {
            buttonColor
        }
    val enabled =
        todayAuth == "0" && status == "opened" && isPhoto && achievementRate.toDouble() < 100f
    val authType = if (isPhoto) "사진 인증" else if (isTime) "이용 시간 인증" else if (isCheckIn) "출석 인증" else ""
    Card(
        modifier = modifier.clickable { onCardClick() },
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        backgroundColor = DefaultWhite,
        border = BorderStroke(1.dp, Color(0xffebebeb)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 20.dp, 20.dp)
        ) {
            Row(
                modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 19.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProgressLabel(
                    text = progressStatus,
                    backgroundColor = background,
                    textColor = textColor
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = myTypography.default,
                    color = Color(0xff6c6c6c),
                    fontSize = dpToSp(dp = 16.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xfffafafa))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp), Arrangement.SpaceBetween
            ) {
                Text(
                    text = authType,
                    style = myTypography.default,
                    color = Color(0xff6c6c6c),
                    fontSize = dpToSp(dp = 13.dp),
                )

                Text(
                    text = "${count}/${total} (달성률 $achievementRate%)",
                    style = myTypography.default,
                    color = Color(0xff6c6c6c),
                    fontSize = dpToSp(dp = 13.dp),
                )

            }
//            if (progressStatus == "종료"){
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(), Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = "",
//                        style = myTypography.default,
//                        color = Color(0xff6c6c6c),
//                        fontSize = dpToSp(dp = 13.dp),
//                    )
//
//                    Text(
//                        text = "",
//                        style = myTypography.default,
//                        color = Color(0xff6c6c6c),
//                        fontSize = dpToSp(dp = 13.dp),
//                    )
//
//                }
//            } else {
                ChallengeStatusButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    text = btnName,
                    backgroundColor = btnColor,
                    onClick = { onClick() },
                    isRemainTime = isRemainTime,
                    status = status,
                    textColor = btnTextColor,
                    enabled = enabled
                )
//            }
        }
    }
}