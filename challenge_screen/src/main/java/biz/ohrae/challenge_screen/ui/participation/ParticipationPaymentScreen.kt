package biz.ohrae.challenge_screen.ui.participation

import android.webkit.CookieManager
import android.webkit.WebSettings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import biz.ohrae.challenge_repo.data.remote.Routes
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import timber.log.Timber

@Composable
fun ParticipationPaymentScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val state = rememberWebViewState("")
        WebView(
            state = state,
            onCreated = {
                it.settings.javaScriptEnabled = true
                it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(it, true)
                val url = Routes.HOST_NAME.dropLast(1) + Routes.REQUEST_PAYMENT
                Timber.e("url : $url")
                it.postUrl(url, getPostData().toByteArray())
            }
        )
    }
}

private fun getPostData(): String {
    var postData = ""
    postData += "user_id=ads"
    postData += "&challenge_id=asd"
    postData += "&paid_amount=0"
    postData += "&rewards_amount=0"
    postData += "&pay_method=asd"
    postData += "&pg=1"

    return postData
}