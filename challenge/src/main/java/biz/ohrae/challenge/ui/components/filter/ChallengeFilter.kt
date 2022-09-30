package biz.ohrae.challenge.ui.components.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import biz.ohrae.challenge.model.filter.FilterItem
import biz.ohrae.challenge.ui.theme.DefaultWhite

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun ChallengeFilterGallery() {
    val certifiedList = listOf(
        FilterItem("매일","daily"),
        FilterItem("평일만","weekend"),
        FilterItem("주말만","weekday"),
        FilterItem("주1회","1"),
        FilterItem("주2회","2"),
        FilterItem("주3회","3"),
        FilterItem("주4회","4"),
        FilterItem("주5회","5"),
        FilterItem("주6회","6"),
    )
    val periodList = listOf(
        FilterItem("주1회","1"),
        FilterItem("주2회","2"),
        FilterItem("주3회","3"),
    )
    val etcList = listOf(
        FilterItem("18세 미만 참여불가 ","1"),
    )

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