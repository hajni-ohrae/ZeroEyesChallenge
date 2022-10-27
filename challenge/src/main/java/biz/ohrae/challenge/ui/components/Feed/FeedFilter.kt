package biz.ohrae.challenge.ui.components.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R


@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun FeedFilterGallery() {
    Column(
        modifier = Modifier
            .background(DefaultWhite)
    ) {
        FeedFilter()
    }
}

@Composable
fun FeedFilter(
    modifier: Modifier = Modifier,
    order: String = "최신순",
    onOrder: () -> Unit = {},
    onMine: () -> Unit = {},
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(Color(0xfff7f7f7))
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(24.dp, 0.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier,
                onClick = {
                    onOrder()
                },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = order,
                        fontSize = dpToSp(dp = 14.dp),
                        style = myTypography.w500,
                        color = Color(0xff606060)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.s_icon_sorting),
                        modifier = modifier.size(18.dp),
                        contentDescription = "s_icon_sorting"
                    )
                }
            }
            TextButton(onClick = { onMine() }) {
                Text(
                    text = "내 인증만 보기",
                    fontSize = dpToSp(dp = 14.dp),
                    style = myTypography.w500,
                    color = Color(0xff606060)
                )
            }
        }
    }

}