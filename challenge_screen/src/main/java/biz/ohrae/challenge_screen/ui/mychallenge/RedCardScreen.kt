package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.banner.FlatBanner
import biz.ohrae.challenge.ui.components.button.ArrowTextButton
import biz.ohrae.challenge.ui.components.list_item.RedCardHistoryItem
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun RedCardScreen(
    clickListener: MyChallengeClickListener? = null
) {
    Column(modifier = Modifier.background(DefaultWhite)) {
        Column(modifier = Modifier.padding(24.dp, 0.dp)) {
            Text(text = "레드카드", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "레드카드 3회부터는 서비스 이용이 영구제한 됩니다\n" +
                        "하단의 레드카드 정책을 확인 해주세요",
                style = myTypography.w500,
                fontSize = dpToSp(dp = 12.dp),
                color = Color(0xff828282),
                lineHeight = dpToSp(dp = 17.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ArrowTextButton(
                text = "레드카드 정책 보러가기",
                onClick = { clickListener?.onClickPolicy(MyChallengeActivity.RED_CARD) }

            )
            Spacer(modifier = Modifier.height(16.dp))

            FlatBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6.37f),
                backgroundColor = Color(0xfffbefef),
                title = "레드카드",
                titleColor = Color(0xff6c6c6c),
                content = "2회",
                contentColor = Color(0xffff0000)
            )
            Spacer(modifier = Modifier.height(21.dp))

        }
        Column(modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xfffafafa))) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 0.dp),
            ) {
                item {
                    RedCardHistoryItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 99.dp),
                        date = "2022.04.25  09:33",
                        title = "책읽기 챌린지",
                        content = "인증규정관 무관한 이미지로 인증을 대체"
                    )
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xfffafafa)))
                }
            }
        }
    }
}