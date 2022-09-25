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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_component.R

@Preview(
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ColoredDropDownGallery() {
    val list = listOf(
        DropDownItem(label = "SK", value = "SK"),
        DropDownItem(label = "KT", value = "KT"),
        DropDownItem(label = "LGU+", value = "LG"),
        DropDownItem(label = "알뜰폰", value = "frugal"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp, 0.dp)
    ) {
        ColoredDropDown(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7.1f),
            label = "휴대폰번호",
            list = list,
        )
        Spacer(modifier = Modifier.height(10.dp))
        ColoredDropDown(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(2.2f),
            label = "",
            horizontalPadding = 8.dp,
            list = list,
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ColoredDropDown(
    modifier: Modifier = Modifier,
    label: String,
    list: List<DropDownItem>,
    backgroundColor: Color = Color(0xfff3f8ff),
    contentColor: Color = Color(0xff4985f8),
    horizontalPadding: Dp = 18.dp,
    onSelectItem: (item: DropDownItem) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(list[0]) }
    var cardWidth by remember { mutableStateOf(Size.Zero) }

    Column(modifier = Modifier.fillMaxWidth()) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = myTypography.default,
                fontSize = dpToSp(dp = 10.dp),
                color = Color(0xff6c6c6c)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = modifier.onSizeChanged {
                cardWidth = Size(it.width.toFloat(), it.height.toFloat())
            },
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(1.dp, backgroundColor),
            elevation = 0.dp,
            onClick = {
                expanded = true
            },
            backgroundColor = backgroundColor
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontalPadding, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = selectedItem.label,
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 14.dp),
                        color = contentColor
                    )
                    Icon(
                        modifier = Modifier.fillMaxHeight(.5f).aspectRatio(1f),
                        painter = painterResource(id = R.drawable.arrow_slidedown),
                        contentDescription = "arrow_slidedown",
                        tint = contentColor
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { cardWidth.width.toDp() })
                        .background(
                            backgroundColor
                        ),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                ) {
                    list.forEach {
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxSize().background(backgroundColor),
                            contentPadding = PaddingValues(horizontalPadding, 0.dp),
                            onClick = {
                                expanded = false
                                selectedItem = it
                                onSelectItem(it)
                            }
                        ) {
                            Column {
                                Text(
                                    text = it.label,
                                    style = myTypography.bold,
                                    fontSize = dpToSp(dp = 14.dp),
                                    color = contentColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}