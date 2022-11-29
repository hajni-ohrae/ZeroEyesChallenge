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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.ChallengeStatusButton
import biz.ohrae.challenge.ui.components.button.FlatButton
import biz.ohrae.challenge.ui.components.detail.getRemainTime
import biz.ohrae.challenge.ui.components.label.ProgressLabel
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.appColor
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

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
            Color(0xff5c94ff)
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
            Color(0xff219653)
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

            Color(0xff6c6c6c)
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
) {
//    val achievementRate = (count.toDouble() / maxPeople.toDouble() * 100.0).roundToInt()
    var remainTime by remember { mutableStateOf(getRemainTime(startDay)) }
    var isRemainTime by remember { mutableStateOf(!remainTime.startsWith("-")) }

    LaunchedEffect(remainTime) {
        delay(1000 * 60)
        remainTime = getRemainTime(startDay)
        isRemainTime = !remainTime.startsWith("-")
    }
    var btnName = if (status == "register") remainTime.replace("-", "") + " 남음" else buttonName
    var btnTextColor = if (status == "register") Color(0xff4985f8) else buttonTextColor
    var btnColor = if (status == "register") DefaultWhite else buttonColor

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
                    text = "인증현황",
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
            ChallengeStatusButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6.5f),
                text = btnName,
                backgroundColor = btnColor,
                onClick = { onClick() },
                isRemainTime = isRemainTime,
                status = status,
                textColor = btnTextColor
            )
        }
    }
}