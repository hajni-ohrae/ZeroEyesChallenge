package biz.ohrae.challenge.ui.test

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatButton

@Composable
fun TestMainScreen(
    clickListener: TestMainClickListener
) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        FlatButton(
            modifier = Modifier.fillMaxWidth().aspectRatio(6.4f),
            text = "TEST DROP DOWN",
            onClick = { clickListener.onClickTestDropDown() }
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlatButton(
            modifier = Modifier.fillMaxWidth().aspectRatio(6.4f),
            text = "TEST EDIT TEXT",
            onClick = { clickListener.onClickTestEditText() }
        )
    }
}