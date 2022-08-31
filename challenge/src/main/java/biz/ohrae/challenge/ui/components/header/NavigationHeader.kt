package biz.ohrae.challenge.ui.components.header

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun NavigationHeaderGallery() {
    NavigationHeader(
        onClickBack = {},
        onClickShare = {}
    )
}

@Composable
fun NavigationHeader(
    onClickBack: () -> Unit,
    onClickShare: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(6.43f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                onClickBack()
            }
        ) {
            Icon(modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "ArrowBack")
        }
        IconButton(
            onClick = {
                onClickShare()
            }
        ) {
            Icon(imageVector = Icons.Outlined.Share, contentDescription = "Share")
        }
    }
}