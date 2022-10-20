package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.card.ChallengeCardItem
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge.util.challengeVerificationPeriodMap
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_repo.util.prefs.Utils.getAuthType
import biz.ohrae.challenge_repo.util.prefs.Utils.getOpenType
import biz.ohrae.challenge_screen.model.main.MainScreenState
import biz.ohrae.challenge_screen.ui.main.MainClickListener
import timber.log.Timber


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun SavedChallengeScreen(
    mainScreenState: MainScreenState? = MainScreenState.mock(),
    clickListener: MainClickListener? = null
) {
    Column() {
        if (!mainScreenState?.challengeList.isNullOrEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(24.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(mainScreenState?.challengeList!!, key = { _, item -> item.id }) { index, item ->
                    val startDay = Utils.getRemainTimeDays(item.start_date.toString())
                    val type = challengeVerificationPeriodMap[item.verification_period_type]
                    val weekType = if (type.isNullOrEmpty()) "주${item.per_week}회 인증" else type
                    ChallengeCardItem(
                        index,
                        item.id,
                        item.goal.toString(),
                        item.user?.getUserName(),
                        startDay.toString(),
                        item.period.toString(),
                        weekType.toString(),
                        item.summary?.total_user_cnt,
                        getAuthType(item),
                        getOpenType(item),
                        item.is_adult_only,
                        onClick = {
                            Timber.e("chall id : ${item.id}")
                            clickListener?.onClickChallengeItem(it)
                        },
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 0.dp),
                text = "저장한 챌린지가 없습니다.",
                style = myTypography.w500,
                fontSize = dpToSp(dp = 20.dp),
                color = Color(0xff828282)
            )
        }
    }
}