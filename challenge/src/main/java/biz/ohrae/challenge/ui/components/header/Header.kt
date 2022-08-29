//package biz.ohrae.challenge.ui.components.header
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import biz.ohrae.challenge.ui.components.avatar.circularAvatar
//import biz.ohrae.challenge.ui.theme.dpToSp
//import biz.ohrae.challenge.ui.theme.myTypography
//
//@Preview(
//    widthDp = 360,
//    showBackground = true
//)
//@Composable
//private fun HeaderGallery() {
//    Column {
//        Header()
//    }
//
//}
//
//@Composable
//fun Header(
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(24.dp, 12.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = "Challenge", style = myTypography.extraBold, fontSize = dpToSp(dp = 20.dp))
//        circularAvatar(Modifier.size(48.dp))
//    }
//}