package biz.ohrae.challenge.ui.components.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.list_item.ChallengeItemData
import biz.ohrae.challenge.model.state.ChallengeDetailStatus
import biz.ohrae.challenge.ui.components.card.CategorySurFace
import biz.ohrae.challenge.ui.components.label.ChallengeProgressStatus
import biz.ohrae.challenge.ui.components.label.ProgressLabel
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ChallengeDetailsTitleGallery() {
    val challengeItemData = ChallengeItemData.mock()

    Column(modifier = Modifier.background(DefaultWhite)) {
        ChallengeDetailsTitle(
            challengeItemData.state,
            challengeItemData.personnel,
            challengeItemData.title,
            challengeItemData.startDate,
            challengeItemData.endDate
        )
        Spacer(modifier = Modifier.height(10.dp))
        ChallengeJoinedDetailsTitle(
            challengeItemData.state,
            challengeItemData.personnel,
            challengeItemData.title,
            isFree = true,
            isAdult = true,
            isPhoto = true,
            challengeItemData.startDate,
            challengeItemData.endDate
        )
    }
}

@Composable
fun ChallengeDetailsTitle(
    status: ChallengeDetailStatus,
    personnel: Int,
    detailTitle: String,
    startDay: String,
    endDay: String
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ProgressLabel(
                text = status.status,
                backgroundColor = status.backgroundColor,
                textColor = status.textColor,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.icon_user),
                contentDescription = "icon_user",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "${personnel}명 참여중",
                fontSize = dpToSp(dp = 12.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = detailTitle, fontSize = dpToSp(dp = 20.dp),
            style = myTypography.extraBold,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "챌린지 시작까지", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChallengeRemainTime(startDay = startDay)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "챌린지 기간", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${convertDate(startDay)} ~ ${convertDate(endDay)}",
            fontSize = dpToSp(dp = 16.dp)
        )
    }
}

@Composable
fun ChallengeJoinedDetailsTitle(
    status: ChallengeDetailStatus,
    personnel: Int,
    detailTitle: String,
    isFree: Boolean,
    isAdult: Boolean,
    isPhoto: Boolean,
    startDay: String,
    endDay: String
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CategorySurFace(
                text = if (isFree) "무료" else "유료",
                backgroundColor = Color(0x335c94ff),
                textColor = Color(0xff5c94ff)
            )
            if (isPhoto) {
                Spacer(modifier = Modifier.width(4.dp))
                CategorySurFace(
                    text = "사진인증",
                    backgroundColor = Color(0xffdedede),
                    textColor = Color(0xff7c7c7c)
                )
            }
            if (isAdult) {
                Spacer(modifier = Modifier.width(4.dp))
                CategorySurFace(
                    text = "18세미만 참여불가",
                    backgroundColor = Color(0x33c27247),
                    textColor = Color(0xffc27247)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = detailTitle, fontSize = dpToSp(dp = 20.dp),
            style = myTypography.extraBold,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "챌린지 기간", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${convertDate(startDay)} ~ ${convertDate(endDay)}",
            fontSize = dpToSp(dp = 16.dp)
        )
    }
}

@Composable
fun ChallengeRemainTime(startDay: String) {
    var remainTime by remember { mutableStateOf(getRemainTime(startDay)) }

    LaunchedEffect(remainTime) {
        delay(1000 * 60)
        remainTime = getRemainTime(startDay)
    }

    ChallengeProgressStatus(
        modifier = Modifier.fillMaxWidth(),
        textColor = Color(0xff4985f8),
        text = remainTime,
        isRemainTime = true,
        backgroundColor = Color(0xfff3f8ff)
    )
}

private fun convertDate(dateStr: String): String {
    return try {
        val dateString = dateStr.replace("T", " ").replace("Z", "")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("M월 d일(E)", Locale.KOREA)
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        outputFormat.format(date!!)
    } catch (ignore: Exception) {
        dateStr
    }
}

private fun getRemainTime(startDay: String): String {
    return try {
        val dateString = startDay.replace("T", " ").replace("Z", "")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA)
        val date = inputFormat.parse(dateString)
        var remain: Long = 0
        val result = StringBuilder()

        if (date != null) {
            remain = date.time - Date().time
            if (remain > 0) {
                val days = ((((remain / 1000) / 60) / 60) / 24)
                val hours = ((((remain / 1000) / 60) / 60) % 24)
                val minutes = (((remain / 1000) / 60) % 60)

                if (days > 0) {
                    result.append(days.toString().padStart(2, '0') + "일 ")
                }
                result.append(hours.toString().padStart(2, '0') + "시간 ")
                result.append(minutes.toString().padStart(2, '0') + "분")
            }
        }
        result.toString()
    } catch (ignore: Exception) {
        ignore.printStackTrace()
        ""
    }
}
