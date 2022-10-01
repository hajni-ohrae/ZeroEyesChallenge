package biz.ohrae.challenge.ui.components.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.filter.FilterItem
import biz.ohrae.challenge.ui.components.checkBox.MyCheckBox
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.myTypography

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun ChallengeFilterItemGallery() {
    val list = listOf(
        FilterItem("전체", "all"),
        FilterItem("유료", "paid"),
        FilterItem("무료", "free"),
    )
    Column(
        modifier = Modifier
            .background(DefaultWhite)
    ) {
        ChallengeFilterItemList(modifier = Modifier, title = "인증빈도", list = list)
    }
}

@Composable
fun ChallengeFilterItemList(
    modifier: Modifier = Modifier,
    title: String = "",
    list: List<FilterItem>,
    textStyle: TextStyle = myTypography.w700,
    select: Boolean = true
) {
    var checked by remember { mutableStateOf(select) }

    Column(
        modifier = Modifier
            .background(DefaultWhite)
    ) {
        Text(
            modifier = Modifier
                .padding(0.dp, 19.dp), text = title, style = textStyle
        )
        LazyVerticalGrid(
            userScrollEnabled = false,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(17.dp),
            horizontalArrangement = Arrangement.spacedBy(3.5.dp),
        ) {
            items(list) {
                MyCheckBox(
                    checkBoxSize = 20.dp,
                    label = it.name,
                    labelStyle = myTypography.w700,
                    onClick = {
                        checked = !checked
                    },
                    onChecked = {
                        checked = !checked
                    }
                )
            }
        }
    }
}