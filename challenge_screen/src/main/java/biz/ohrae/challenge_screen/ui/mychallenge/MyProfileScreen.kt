package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.user.RewardData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.util.prefs.SharedPreference


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun MyProfileScreen(
    user: User? = null,
    prefs: SharedPreference? = null,
    select: Boolean = true,
    clickListener: MyChallengeClickListener? = null,
) {
    Column() {
        Text(text = "닉네임 (챌린지 참여 시 사용됩니다)", fontSize = dpToSp(dp = 14.dp), style = myTypography.bold)
    }
}