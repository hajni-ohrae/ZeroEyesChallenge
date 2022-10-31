package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import biz.ohrae.challenge_repo.model.user.RewardData
import biz.ohrae.challenge_repo.model.user.User
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailClickListener


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun MyProfileScreen(
    user: User? = null,
    select: Boolean = true,
    clickListener: ChallengeDetailClickListener? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DefaultWhite)
    ) {
        profileTitle("닉네임 (챌린지 참여 시 사용됩니다)")
        ProfileItemButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            user = user,
            buttonName = "저장",
            onClick = { })

        profileTitle("휴대폰 번호")
        ProfileItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            title = user?.phone_number.toString(),
            onClick = { })
        profileTitle("실명 (본인 인증과 동시에 자동 입력됩니다)")
        ProfileItemButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.46f),
            user = user,
            buttonName = "본인 인증",
            onClick = { })

    }
}

@Composable
fun profileTitle(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfff8f8f8))
    ) {
        Text(
            modifier = Modifier
                .padding(24.dp, 18.dp),
            text = title,
            fontSize = dpToSp(dp = 14.dp),
            style = myTypography.bold,
            color = Color(0xff707070)
        )
    }
}

@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    onClick: () -> Unit,
) {
    Column(modifier = modifier
        .padding(24.dp, 0.dp)
        .clickable {
            onClick()
        }) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = TextBlack,
                fontSize = dpToSp(dp = 16.dp),
                style = myTypography.bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (description != null) {
                Text(
                    text = description,
                    style = myTypography.default,
                    color = Color(0xffff5800),
                    fontSize = dpToSp(dp = 14.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.5.dp))
            Icon(
                painter = painterResource(id = R.drawable.icon_black_arrow_1),
                contentDescription = description,
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun ProfileItemButton(
    modifier: Modifier = Modifier,
    user: User?,
    buttonName: String,
    onClick: () -> Unit,
) {
    Column(modifier = modifier
        .padding(24.dp, 0.dp)
        .clickable {
            onClick()
        }) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier,
                text = user?.nickname.toString(),
                fontSize = dpToSp(dp = 16.dp),
                style = myTypography.bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Card(
                modifier = Modifier
                    .clickable(onClick = { }),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xff005bad))
                ) {
                    Text(
                        modifier = Modifier.padding(15.dp, 9.dp),
                        text = buttonName,
                        fontSize = dpToSp(dp = 12.dp),
                        style = myTypography.bold,
                        color = DefaultWhite
                    )
                }
            }
        }
    }
}