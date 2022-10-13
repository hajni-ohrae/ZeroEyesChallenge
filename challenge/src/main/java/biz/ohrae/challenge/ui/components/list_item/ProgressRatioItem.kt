package biz.ohrae.challenge.ui.components.list_item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ProgressRatioItemGallery() {
    val list = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp, 0.dp)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(10),
            userScrollEnabled = false,
            verticalArrangement = Arrangement.spacedBy(3.5.dp),
            horizontalArrangement = Arrangement.spacedBy(3.5.dp),
        ) {
            items(list) {
                if (it.toInt() <= 3) {
                    ProgressRatioItem(isSuccess = false, number = it)
                } else if (it.toInt() <= 6) {
                    ProgressRatioFailItem()
                } else {
                    ProgressRatioItem(isSuccess = true, number = it)
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun ProgressRatioItem(
    modifier: Modifier = Modifier,
    isSuccess: Boolean = false,
    number: String = "1",
) {
    val backgroundColor = if (isSuccess) {
        remember { mutableStateOf(Color(0xff003865)) }
    } else {
        remember { mutableStateOf(Color(0xffe6e6e6)) }
    }
    val textColor = if (isSuccess) {
        remember { mutableStateOf(DefaultWhite) }
    } else {
        remember { mutableStateOf(Color(0xff6c6c6c)) }
    }

    Column(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .background(backgroundColor.value, RoundedCornerShape(2.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = number,
            style = myTypography.bold,
            fontSize = dpToSp(dp = 14.dp),
            color = textColor.value
        )
    }
}

@Composable
fun ProgressRatioFailItem(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.icon_auth_fail),
            contentScale = ContentScale.Fit,
            contentDescription = "icon_auth_fail"
        )
    }
}
