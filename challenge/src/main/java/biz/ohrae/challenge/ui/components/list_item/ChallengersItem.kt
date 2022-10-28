package biz.ohrae.challenge.ui.components.list_item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengersItemGallery() {
    val challengers = listOf<String>("123", "234")
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(challengers) { item ->
            ChallengersItem(
                userName = item
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ChallengersItem(
    modifier: Modifier = Modifier,
    userName: String = "",
    imagePath: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(8.66f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        circularAvatar(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            url = imagePath.toString()
        )
        Spacer(modifier = Modifier.fillMaxWidth(0.058f))
        Text(
            text = userName,
            style = myTypography.default,
            fontSize = dpToSp(dp = 14.dp),
            color = DefaultBlack
        )
    }
}