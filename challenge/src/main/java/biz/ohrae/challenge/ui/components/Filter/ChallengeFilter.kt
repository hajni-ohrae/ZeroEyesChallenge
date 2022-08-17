package biz.ohrae.challenge.ui.components.Filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun ChallengeFilterGallery() {
    val certifiedList = listOf("매일", "평일만", "주말만", "주1회", "주2회", "주3회", "주4회", "주5회", "주6회")
    val periodList = listOf("1주","2주","3주")
    val etcList = listOf("18세 미만 참여불가")

    Column(
        modifier = Modifier
            .background(DefaultWhite)
    ) {
        ChallengeFilter()
        ChallengeFilterItem(modifier = Modifier, title = "인증빈도", list = certifiedList)
        ChallengeFilterItem(modifier = Modifier, title = "챌린지 기간", list = periodList)
        ChallengeFilterItem(modifier = Modifier, title = "기타", list = etcList)
    }

}

@Composable
fun ChallengeFilter(
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth()) {
        Row(modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "필터")
            TextButton( onClick = { /*TODO*/ }) {
                Text( text = "취소",color = Color(0xff747474))
            }
        }
    }

}