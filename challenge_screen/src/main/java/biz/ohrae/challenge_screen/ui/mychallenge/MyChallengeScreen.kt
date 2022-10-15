package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.components.card.ChallengesInParticipationCard
import biz.ohrae.challenge.ui.components.card.MyChallengeIngoBox
import biz.ohrae.challenge.ui.components.menu.MenuItem
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.model.user.UserChallengeListState


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun MyChallengeScreen(
    challengeData: ChallengeData = ChallengeData.mock(),
    user: User? = null,
    clickListener: MyChallengeClickListener? = null,
    userChallengeListState: UserChallengeListState? = null
) {
    val availableRewards by remember {
        mutableStateOf(challengeData.user?.rewards_amount ?: 0)
    }

    Column(
        modifier = Modifier
            .padding(24.dp, 0.dp)
            .fillMaxWidth()
            .fillMaxSize()
            .background(DefaultWhite)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    ItemHeader(
                        user = user,
                        availableRewards = availableRewards.toString(),
                        clickListener = clickListener,
                        userChallengeListState = userChallengeListState
                    )
                }
                if (userChallengeListState != null) {
                    items(userChallengeListState?.userChallengeList!!) { item ->
                        val inChallenge = item.inChallenge?.get(0)
                        ChallengesInParticipationCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = item.goal.toString(),
                            count = inChallenge?.today_verified_cnt.toString(),
                            maxPeople = inChallenge?.verified_cnt.toString(),
                            progressStatus = item.status,
                            achievementRate = inChallenge?.achievement_percent.toString(),
                            Utils.userChallengeBackground(item.status),
                            Utils.userChallengeTextColor(item.status),
                            onClick = { clickListener?.onClickChallengeAuthItem(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemHeader(
    user: User? = null,
    availableRewards: String,
    select: Boolean = true,
    clickListener: MyChallengeClickListener? = null,
    userChallengeListState: UserChallengeListState? = null
) {

    Column(Modifier.fillMaxWidth()) {

        Column() {
            Row {
                circularAvatar(modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Text(
                        text = user?.getUserName() ?: "이름",
                        style = myTypography.w700,
                        fontSize = dpToSp(dp = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = user?.phone_number.toString(),
                        style = myTypography.w700,
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color(0xff4f4f4f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        MyChallengeIngoBox(
            user?.summary?.attend_cnt.toString(),
            user?.summary?.achievement_cnt.toString(),
            user?.summary?.achievement_percent.toString()
        )
        Spacer(modifier = Modifier.height(8.dp))
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_coin,
            title = "보유 리워즈",
            description = "${Utils.numberToString(availableRewards.toString())}원",
            onClick = { clickListener?.onClickReward() }
        )
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            resId = R.drawable.icon_card,
            title = "결제내역",
            onClick = { clickListener?.onClickPaymentDetail() }
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
//        if (userChallengeListState != null) {
//            Row(modifier = Modifier.padding(0.dp, 22.dp)) {
//                PaidFilterCard(modifier = Modifier, text = "전체", select = select)
//                Spacer(modifier = Modifier.width(4.dp))
//                PaidFilterCard(modifier = Modifier, text = "모집중")
//                Spacer(modifier = Modifier.width(4.dp))
//                PaidFilterCard(modifier = Modifier, text = "진행중")
//                PaidFilterCard(modifier = Modifier, text = "완료")
//            }
//            LazyColumn(
//                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.spacedBy(17.dp),
//            ) {
//                items(userChallengeListState?.userChallengeList!!) { item ->
//                    ChallengesInParticipationCard(
//                        modifier = Modifier.fillParentMaxSize(),
//                        title = item.goal.toString(),
//                        1,
//                        30,
//                        "완료",
//                        Color(0xffdedede),
//                        Color(0xff6c6c6c)
//                    )
//                }
//            }
//        } else {
//            Text(
//                text = "참여중인 챌린지 없음",
//                style = myTypography.bold,
//                fontSize = dpToSp(dp = 16.dp),
//                color = DefaultBlack
//            )
//        }
    }
}

