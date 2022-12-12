package biz.ohrae.challenge.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R


@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun MainTopCardGallery() {
    Column() {
        MainTopCard(Modifier, "챌린지 이렇게\n이용해보세요")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainTopCard(
    modifier: Modifier, content: String, onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
    ) {
        Card(
            modifier = modifier,
            onClick = {
                onClick()
            },
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xffE48B57))
                    .padding(24.dp)
            ) {
                Text(
                    text = "챌린지 이렇게 \n이용하세요",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 30.dp),
                    color = DefaultWhite,
                )
                Spacer (modifier = Modifier.height(10.dp))

                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        painter = painterResource(id = R.drawable.banner_icon),
                        contentDescription = "banner_icon",

                        )
                }
            }
        }
    }
}