package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.components.card.ChallengesInParticipationCard
import biz.ohrae.challenge.ui.components.card.MyChallengeIngoBox
import biz.ohrae.challenge.ui.components.card.PaidFilterCard
import biz.ohrae.challenge.ui.components.menu.MenuItem
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.prefs.SharedPreference


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun MyChallengeScreen(
    challengeData: ChallengeData = ChallengeData.mock(),
    prefs: SharedPreference? = null,
    select: Boolean = true,
    clickListener: MyChallengeClickListener? = null
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(24.dp, 0.dp)
            .fillMaxHeight()
            .background(DefaultWhite)
            .verticalScroll(scrollState)
    ) {
        Column() {
            Row {
                circularAvatar(modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Text(
                        text = "박보검",
                        style = myTypography.w700,
                        fontSize = dpToSp(dp = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "010-0000-0000",
                        style = myTypography.w700,
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color(0xff4f4f4f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        MyChallengeIngoBox("12", "10")
        Spacer(modifier = Modifier.height(8.dp))
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_coin,
            title = "보유 리워즈",
            description = "125,000원",
            onClick = { clickListener?.onClickReward() }
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_card,
            title = "결제내역",
            onClick = {clickListener?.onClickPaymentDetail()}
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_like,
            title = "저장한 챌린지",
            onClick = { clickListener?.onClickSavedChallenge() }
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_note,
            title = "레드카드",
            onClick = { clickListener?.onClickRedCard() }
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "참여한 챌린지",
            style = myTypography.bold,
            fontSize = dpToSp(dp = 16.dp),
            color = DefaultBlack
        )
        Spacer(modifier = Modifier.height(17.dp))
        Row(modifier = Modifier.padding(0.dp, 22.dp)) {
            PaidFilterCard(modifier = Modifier, text = "전체", select = select)
            Spacer(modifier = Modifier.width(4.dp))
            PaidFilterCard(modifier = Modifier, text = "모집중")
            Spacer(modifier = Modifier.width(4.dp))
            PaidFilterCard(modifier = Modifier, text = "진행중")
            PaidFilterCard(modifier = Modifier, text = "완료")
        }
        ChallengesInParticipationCard(
            Modifier,
            title = "매일 6시간씩 한국사 공부",
            1,
            30,
            "완료",
            Color(0xffdedede),
            Color(0xff6c6c6c)
        )
    }
}