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
import biz.ohrae.challenge.ui.theme.GrayColor10
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*


@Preview(
    showBackground = true,
    backgroundColor = 0xffb9b9b9,
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
            challengeItemData.endDate,
            "사진인증 (즉석 촬영으로만 인증 가능)"
        )
        Spacer(modifier = Modifier.height(20.dp))
        ChallengeJoinedDetailsTitle(
            challengeItemData.state,
            challengeItemData.personnel,
            challengeItemData.title,
            isFree = true,
            ageType = "18세 이상 전용",
            isPhoto = true,
            isTime = true,
            isCheckIn = true,
            challengeItemData.startDate,
            challengeItemData.endDate,
            "출석 인증 (입실 시 자동 인증)\n이용권 필요"
        )
    }
}

@Composable
fun ChallengeDetailsTitle(
    status: ChallengeDetailStatus,
    personnel: Int,
    detailTitle: String,
    startDay: String,
    endDay: String,
    authMethod: String,
    ageLimitType:String = ""
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
            text = "인증 방법", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = authMethod,
            fontSize = dpToSp(dp = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "참여 나이", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = ageLimitType,
            fontSize = dpToSp(dp = 16.dp)
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
fun ChallengeJoinedDetailsTitle(
    status: ChallengeDetailStatus,
    personnel: Int,
    detailTitle: String,
    isFree: Boolean,
    ageType: String,
    isPhoto: Boolean,
    isTime: Boolean,
    isCheckIn: Boolean,
    startDay: String,
    endDay: String,
    authMethod: String,
    ) {
    val ageBackgroundColor = if (ageType == "18세 이상 전용") Color(0x33ffadad) else Color(0x33a2cc5e)
    val ageTextColor = if (ageType == "18세 이상 전용") Color(0xffd98181) else Color(0xff73b00e)

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = detailTitle, fontSize = dpToSp(dp = 20.dp),
            style = myTypography.extraBold,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "인증 방법", fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = authMethod,
            fontSize = dpToSp(dp = 16.dp)
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
    var isRemainTime by remember { mutableStateOf(!remainTime.startsWith("-")) }

    LaunchedEffect(remainTime) {
        delay(1000 * 60)
        remainTime = getRemainTime(startDay)
        isRemainTime = !remainTime.startsWith("-")
    }

    ChallengeProgressStatus(
        modifier = Modifier.fillMaxWidth(),
        textColor = Color(0xff4985f8),
        text = remainTime.replace("-", ""),
        isRemainTime = isRemainTime,
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

fun getRemainTime(startDay: String): String {
    return try {
        var dateString = startDay.replace("T", " ").replace("Z", "")
        dateString = dateString.substring(0, 19)
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)

        val date = inputFormat.parse(dateString)
        var remain: Long = 0
        val result = StringBuilder()
        var preFix = ""

        if (date != null) {
            remain = date.time - Date().time
            if (remain < 0) {
                remain *= -1
                preFix = "-"
            }

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
        preFix + result.toString()
    } catch (ignore: Exception) {
        ignore.printStackTrace()
        ""
    }
}
