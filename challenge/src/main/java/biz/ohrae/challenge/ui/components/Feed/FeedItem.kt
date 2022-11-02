package biz.ohrae.challenge.ui.components.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.components.card.CategorySurFace
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import com.skydoves.landscapist.glide.GlideImage


@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun FeedItemGallery() {
    Column(
        modifier = Modifier
            .background(DefaultWhite)
    ) {
        FeedItem()
    }
}

@Composable
fun FeedItem(
    imageUrl: String = "",
    username: String = "아이오",
    count: Int = 1,
    date: String = "2022.05.09",
    comment: String = "123",
    type: String = "photo",
    onReport: () -> Unit = {},
    onLike: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(DefaultWhite)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                circularAvatar(
                    modifier = Modifier
                        .size(40.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Column() {
                    Text(
                        text = username,
                        color = Color(0xff404040),
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 13.dp)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = date,
                        color = Color(0xff9a9a9a),
                        style = myTypography.w500,
                        fontSize = dpToSp(dp = 12.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = comment,
                style = myTypography.w700,
                fontSize = dpToSp(dp = 12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (type == "photo") {
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
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategorySurFace(
                        backgroundColor = Color(0x335c94ff),
                        textColor = Color(0xff5c94ff),
                        textStyle = myTypography.extraBold,
                        text = "${count}회"
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(0.025f))
                    Text(
                        text = date,
                        style = myTypography.extraBold,
                        fontSize = dpToSp(dp = 20.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xfff5f5f5))
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(24.dp, 5.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier,
                onClick = {
                    onLike()
                },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.s_icon_good),
                        modifier = Modifier.size(18.dp),
                        contentDescription = "s_icon_sorting"
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "좋아요",
                        fontSize = dpToSp(dp = 13.dp),
                        style = myTypography.w500,
                        color = Color(0xff606060)
                    )
                }
            }
            Text(
                modifier = Modifier.clickable(onClick = onReport),
                text = "신고",
                fontSize = dpToSp(dp = 13.dp),
                style = myTypography.w500,
                color = Color(0xff606060),
            )
        }
    }
}
