package biz.ohrae.challenge_screen.ui.mychallenge

import android.webkit.CookieManager
import android.webkit.WebSettings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.components.dropdown.ColoredDropDown
import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge.ui.components.input.LabeledTextField
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.data.remote.Routes
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import timber.log.Timber


@Composable
fun PhoneAuthScreenWebView() {
    Column(modifier = Modifier.fillMaxSize()) {
        val state = rememberWebViewState(Routes.HOST_NAME.dropLast(1) + "/api/challenge/views/checkplus/main/" + "3a6ce792-d0cb-4567-8dd6-f08cb64a1039")
        WebView(
            state = state,
            onCreated = {
                it.settings.javaScriptEnabled = true
                it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(it, true)
                val url = Routes.HOST_NAME.dropLast(1) + "/api/challenge/views/checkplus/main/" + "3a6ce792-d0cb-4567-8dd6-f08cb64a1039"
                Timber.e("url : $url")
                it.postUrl(url, byteArrayOf())
            }
        )
    }
}


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun PhoneAuthScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "휴대폰 본인인증", style = myTypography.w700, fontSize = dpToSp(dp = 20.dp))
            Spacer(modifier = Modifier.height(28.dp))
            AuthPhoneNumberInput()
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(0xffc7c7c7)))
        }
        Spacer(modifier = Modifier.weight(1f))
        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "확인",
            onClick = { },
        )
    }
}

@Composable
private fun AuthPhoneNumberInput() {
    var phoneNumber by remember { mutableStateOf("") }

    Column {
        Text(
            text = "휴대폰번호",
            style = myTypography.default,
            fontSize = dpToSp(dp = 10.dp),
            color = Color(0xff6c6c6c)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .fillMaxWidth(0.3f)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                ColoredDropDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalPadding = 8.dp,
                    label = "",
                    list = phonePublishList
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                LabeledTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    label = "",
                    placeholder = "휴대폰번호 11자리 입력",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    maxLength = 20,
                    value = phoneNumber,
                    visibleDivider = false,
                    onValueChange = {
                        phoneNumber = it
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}


val phonePublishList = listOf(
    DropDownItem(label = "SK", value = "SK"),
    DropDownItem(label = "KT", value = "KT"),
    DropDownItem(label = "LG", value = "LG"),
    DropDownItem(label = "KT 알뜰폰", value = "KT 알뜰폰"),
)
