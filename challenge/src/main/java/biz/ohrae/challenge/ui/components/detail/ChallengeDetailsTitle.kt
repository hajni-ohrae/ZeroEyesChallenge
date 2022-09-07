package biz.ohrae.challenge.ui.components.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.list_item.ChallengeItemData
import biz.ohrae.challenge.ui.components.avatar.Avatar
import biz.ohrae.challenge.ui.components.label.ChallengeProgressStatus
import biz.ohrae.challenge.ui.components.label.ProgressLabel
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ChallengeDetailsTitleGallery() {
    val challengeItemData = ChallengeItemData.mock()

    Column(modifier = Modifier.background(DefaultWhite)) {
        ChallengeDetailsTitle(
            challengeItemData.personnel,
            challengeItemData.title,
            challengeItemData.startDate,
            challengeItemData.endDate
        )
    }
}

@Composable
fun ChallengeDetailsTitle(personnel: Int, detailTitle: String, startDay: String, endDay: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ProgressLabel(
                text = "모집중",
                backgroundColor = Color(0xffebfaf1),
                textColor = Color(0xff219653),
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
        Text(text = detailTitle,fontSize = dpToSp(dp = 20.dp),
            style = myTypography.extraBold,)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "챌린지 시작까지",fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,)
        Spacer(modifier = Modifier.height(8.dp))
        ChallengeProgressStatus(
            modifier = Modifier.fillMaxWidth(),
            textColor = Color(0xff4985f8),
            text = "0일 00시간 00분",
            isRemainTime = true,
            backgroundColor = Color(0xfff3f8ff)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "챌린지 기간",fontSize = dpToSp(dp = 16.dp),
            style = myTypography.bold,)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$startDay ~ $endDay", fontSize = dpToSp(dp = 16.dp))
    }
}

