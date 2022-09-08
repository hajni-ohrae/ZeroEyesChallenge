package biz.ohrae.challenge.ui.test

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge.ui.components.dropdown.MyDropDown

@Composable
fun TestDropdownScreen() {
    val list = listOf(
        DropDownItem(label = "우리은행", value = "우리은행"),
        DropDownItem(label = "국민은행", value = "국민은행"),
        DropDownItem(label = "농협", value = "농협"),
        DropDownItem(label = "토스뱅크", value = "토스뱅크"),
        DropDownItem(label = "카카오뱅크", value = "카카오뱅크"),
    )

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