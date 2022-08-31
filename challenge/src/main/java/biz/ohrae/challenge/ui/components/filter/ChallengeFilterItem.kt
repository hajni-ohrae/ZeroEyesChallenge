package biz.ohrae.challenge.ui.components.filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun ChallengeFilterItemGallery() {
    val list = listOf("매일", "평일만", "주말만", "주1회", "주2회", "주3회", "주4회", "주5회", "주6회")
    Column(
        modifier = Modifier
            .background(DefaultWhite)
    ) {
        ChallengeFilterItem(modifier = Modifier, title = "인증빈도", list = list)
    }
}

@Composable
fun ChallengeFilterItem(
    modifier: Modifier = Modifier,
    title: String = "",
    list: List<String>,
    textStyle: TextStyle = myTypography.w700,
) {
    Column(
        modifier = Modifier
            .background(DefaultWhite)
    ) {
        Text(
            modifier = Modifier
                .padding(0.dp,19.dp),text = title, style = textStyle)
        LazyVerticalGrid(
            userScrollEnabled = false,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(17.dp),
            horizontalArrangement = Arrangement.spacedBy(3.5.dp),
        ) {
            items(list) {
                ChallengeFilterItem(modifier = Modifier, text = it)
            }
        }
    }
}

@Composable
fun ChallengeFilterItem(
    modifier: Modifier = Modifier,
    text: String = "",
) {
    Row() {
        Surface(
            modifier = modifier
                .width(20.dp)
                .height(20.dp)
                .aspectRatio(1f),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color(0xffc7c7c7)),
        ){
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, color = Color(0xff6c6c6c))
    }
}

