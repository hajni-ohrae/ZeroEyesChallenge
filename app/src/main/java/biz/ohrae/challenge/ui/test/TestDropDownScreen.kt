package biz.ohrae.challenge.ui.test

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatButton
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown

@Composable
fun TestDropdownScreen() {
    val list = listOf("우리은행", "국민은행", "농협", "토스뱅크", "카카오뱅크")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp, 0.dp)
    ) {
        MyDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7.1f),
            label = "은행명",
            list = list
        )
    }
}