package biz.ohrae.challenge.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun HeaderGallery() {
    Column {
        Header()
        Spacer(modifier = Modifier.height(10.dp))
        BackButton()
        Spacer(modifier = Modifier.height(10.dp))
        BackButton(isDark = true)
        Spacer(modifier = Modifier.height(10.dp))
        BackButton(isShare = true)
        Spacer(modifier = Modifier.height(10.dp))
        BackButton(title = "챌린지 참여")
        Spacer(modifier = Modifier.height(10.dp))
        BackButton(title = "챌린지 참여", isShare = true)
    }
}

@Composable
fun Header(
    goMyChallenge: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Challenge", style = myTypography.extraBold, fontSize = dpToSp(dp = 20.dp))
        IconButton(
            modifier = Modifier,
            onClick = {
                goMyChallenge()
            }
        ) {
            circularAvatar(Modifier.size(48.dp))
        }
    }
}


@Composable
fun BackButton(
    onBack: () -> Unit = {},
    title: String = "",
    isDark: Boolean = false,
    isShare: Boolean = false,
    onShare: () -> Unit = {},
) {
    val backgroundColor by remember {
        if (isDark) {
            mutableStateOf(TextBlack)
        } else {
            mutableStateOf(DefaultWhite)
        }
    }

    val tint by remember {
        if (isDark) {
            mutableStateOf(DefaultWhite)
        } else {
            mutableStateOf(Color.Unspecified)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(6.43f)
//            .padding(16.dp, 13.dp)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier,
            onClick = {
                onBack()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "icon_back",
                tint = tint,
            )
        }
        if (title.isNotEmpty()) {
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = title,
                textAlign = TextAlign.Center
            )
            if (!isShare) {
                IconButton(
                    modifier = Modifier,
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = "icon_back",
                        tint = if (isDark) TextBlack else DefaultWhite,
                    )
                }
            }
        }
        if (isShare) {
            IconButton(
                modifier = Modifier,
                onClick = {
                    onShare()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_share),
                    contentDescription = "icon_share",
                    tint = tint,
                )
            }
        }
    }
}