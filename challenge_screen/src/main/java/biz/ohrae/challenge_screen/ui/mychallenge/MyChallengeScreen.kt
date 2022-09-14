package biz.ohrae.challenge_screen.ui.mychallenge

import android.service.autofill.UserData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.register.ChallengeData
import biz.ohrae.challenge.ui.components.avatar.Avatar
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.components.card.MyChallengeIngoBox
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_screen.ui.register.RegisterClickListener


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun MyChallengeScreen(
    challengeData: ChallengeData = ChallengeData.mock(),
    user: User? = null
) {
    Column() {
        Row {
            circularAvatar()
            Column() {
                Text(
                    text = user!!.nick_name,
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 16.dp)
                )
                Text(
                    text = user!!.phone_number,
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 16.dp),
                    color = Color(0xff4f4f4f)
                )
            }
        }
        MyChallengeIngoBox("12", "10")
    }
}