package biz.ohrae.challenge_screen.ui.mychallenge

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.util.NiceAuthChromeClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import timber.log.Timber
import java.net.URISyntaxException


@Composable
fun PhoneAuthScreenWebView(userId: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current as BaseActivity
        val state = rememberWebViewState(Routes.HOST_NAME.dropLast(1) + "/views/checkplus/main/$userId")
        WebView(
            state = state,
            onCreated = {
                it.settings.javaScriptEnabled = true
                it.settings.domStorageEnabled = true
                it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                it.settings.setSupportMultipleWindows(true)
                it.settings.javaScriptCanOpenWindowsAutomatically = true

                it.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                it.settings.loadWithOverviewMode = true
                it.settings.builtInZoomControls = true
                it.settings.setSupportZoom(true)
                it.settings.loadWithOverviewMode = true

                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(it, true)
                val url = Routes.HOST_NAME.dropLast(1) + "/views/checkplus/main/$userId"
                Timber.e("url : $url")
//                it.webViewClient = NiceAuthWebViewClient(context)
                it.webChromeClient = NiceAuthChromeClient(context)

                it.loadUrl(url)
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
                .height(60.dp),
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

private fun overrideLoading(context: Context, view: WebView, url: String): Boolean {
    //웹뷰 내 표준창에서 외부앱(통신사 인증앱)을 호출하려면 intent:// URI를 별도로 처리해줘야 합니다.
    //다음 소스를 적용 해주세요.
    Timber.e("overrideLoading url : $url")
    if (url.startsWith("intent://")) {
        var intent: Intent? = null
        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            if (intent != null) {
                //앱실행
                context.startActivity(intent)
            }
        } catch (e: URISyntaxException) {
            //URI 문법 오류 시 처리 구간
        } catch (e: ActivityNotFoundException) {
            val packageName = intent!!.getPackage()
            if (packageName != "") {
                // 앱이 설치되어 있지 않을 경우 구글마켓 이동
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
            }
        }
        //return  값을 반드시 true로 해야 합니다.
        return true
    } else if (url.startsWith("https://play.google.com/store/apps/details?id=") || url.startsWith(
            "market://details?id="
        )
    ) {
        //표준창 내 앱설치하기 버튼 클릭 시 PlayStore 앱으로 연결하기 위한 로직
        val uri: Uri = Uri.parse(url)
        val packageName: String? = uri.getQueryParameter("id")
        if (!packageName.isNullOrEmpty()) {
            // 구글마켓 이동
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
            )
        }
        //return  값을 반드시 true로 해야 합니다.
        return true
    }

    //return  값을 반드시 false로 해야 합니다.
    return false
}