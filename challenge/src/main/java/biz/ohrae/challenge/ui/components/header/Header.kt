package biz.ohrae.challenge.ui.components.header

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.avatar.circularAvatar
import biz.ohrae.challenge.ui.theme.TextBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R
import kotlinx.coroutines.launch

@Preview(
    widthDp = 360,
    showBackground = true
)
@Composable
private fun HeaderGallery() {
    Column {
        Header()
        Spacer(modifier = Modifier.height(30.dp))
        BackButton()
    }

}

@Composable
fun Header(
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Challenge", style = myTypography.extraBold, fontSize = dpToSp(dp = 20.dp))
        circularAvatar(Modifier.size(48.dp))
    }
}



@Composable
fun BackButton(
    onBack: () -> Unit = {},
    title:String  = ""
) {
    Row( modifier = Modifier.padding(16.dp,13.dp),verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            modifier = Modifier,
            onClick = {
                onBack()
            }
        ) {
            Icon(
                modifier = Modifier.rotate(180F),
                painter = painterResource(id = R.drawable.icon_black_arrow_1),
                contentDescription = "icon_black_back",
                tint = TextBlack,
            )
        }
        Text(modifier = Modifier.fillMaxWidth().align(CenterVertically), text = title,
            textAlign = TextAlign.Center)
    }
}