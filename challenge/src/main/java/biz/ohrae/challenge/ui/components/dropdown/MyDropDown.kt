package biz.ohrae.challenge.ui.components.dropdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
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
private fun DropDownGallery() {
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
            list = list,
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyDropDown(
    modifier: Modifier = Modifier,
    label: String,
    list: List<DropDownItem>,
    selectedPosition: Int? = null,
    onSelectItem: (item: DropDownItem) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember {
        mutableStateOf(
            if (selectedPosition == null) {
                list[0]
            } else {
                list[selectedPosition]
            }
        )
    }
    var cardWidth by remember { mutableStateOf(Size.Zero) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = myTypography.default,
            fontSize = dpToSp(dp = 10.dp),
            color = Color(0xff6c6c6c)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = modifier.onSizeChanged {
                cardWidth = Size(it.width.toFloat(), it.height.toFloat())
            },
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(1.dp, Color(0xff959595)),
            elevation = 0.dp,
            onClick = {
                expanded = true
            },
            backgroundColor = DefaultWhite
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedItem.label,
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 16.dp),
                        color = Color(0xff6c6c6c)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_slidedown),
                        contentDescription = "arrow_slidedown",
                        tint = Color.Unspecified
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { cardWidth.width.toDp() })
                        .background(
                            DefaultWhite
                        ),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    list.forEach {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                selectedItem = it
                                onSelectItem(it)
                            }
                        ) {
                            Text(
                                text = it.label,
                                style = myTypography.bold,
                                fontSize = dpToSp(dp = 16.dp),
                                color = Color(0xff6c6c6c)
                            )
                        }
                    }
                }
            }
        }
    }
}