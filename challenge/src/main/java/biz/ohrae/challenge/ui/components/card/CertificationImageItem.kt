package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.model.card.CertificationItemData
import biz.ohrae.challenge.ui.components.avatar.Avatar
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import com.skydoves.landscapist.glide.GlideImage

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun CertificationImageItemGallery() {
    val list = listOf(
        CertificationItemData("", "아이오", 1, "2022.05.09", "누적 08:06"),
        CertificationItemData("", "아이오", 2, "2022.05.09", "누적 08:06"),
        CertificationItemData("", "아이오", 3, "2022.05.09", "누적 08:06"),
        CertificationItemData("", "아이오", 4, "2022.05.09", "누적 08:06"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultWhite)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(24.dp, 0.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { item ->
                CertificationImageItem(item.imageUrl, item.userName, item.count, item.date)
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
fun CertificationImageItem(
    imageUrl: String = "",
    username: String = "아이오",
    count: Int = 1,
    date: String = "2022.05.09",
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        backgroundColor = Color(0xfff9f9f9)
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            imageModel = imageUrl,
            contentScale = ContentScale.Crop,
            previewPlaceholder = R.drawable.img_example,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.89f)
                    .fillMaxHeight(0.842f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1875f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Avatar(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = username,
                        color = DefaultWhite,
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategorySurFace(
                        backgroundColor = Color(0x99121212),
                        textColor = DefaultWhite,
                        textStyle = myTypography.extraBold,
                        text = "${count}회"
                    )
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.106f))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = date,
                    color = DefaultWhite,
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 16.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.068f))
            }
        }
    }
}