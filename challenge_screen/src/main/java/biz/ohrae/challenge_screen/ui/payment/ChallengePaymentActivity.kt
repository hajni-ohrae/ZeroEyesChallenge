package biz.ohrae.challenge_screen.ui.payment

import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import biz.ohrae.challenge_repo.data.remote.Routes
import biz.ohrae.challenge_screen.databinding.ActivityChallengePaymentBinding
import biz.ohrae.challenge_screen.ui.payment.iamport.Bridge
import biz.ohrae.challenge_screen.ui.payment.iamport.NiceWebViewClient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ChallengePaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChallengePaymentBinding

    private var niceClient: NiceWebViewClient? = null
    private val APP_SCHEME = "mooinapp://"

    private var challengeId: String? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        challengeId = intent.getStringExtra("challengeId")
        userId = intent.getStringExtra("userId")

        init()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun init() {
        val webView = binding.webView
        val settings: WebSettings = webView.settings

        settings.javaScriptEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(webView, true)
        }
        niceClient = NiceWebViewClient(this, webView)
        webView.webViewClient = niceClient!!
        webView.webChromeClient = WebChromeClient()
        val bridge = Bridge(this, webView)
        webView.addJavascriptInterface(bridge, "Bridge")
        val intent = intent
        val intentData = intent.data
        if (intentData == null) {
            payment()
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            val url = intentData.toString()
            if (url.startsWith(APP_SCHEME)) {
                val redirectURL = url.substring(APP_SCHEME.length + 3) //"://"가 추가로 더 전달됨
                webView.loadUrl(redirectURL)
            }
        }
    }

    private fun payment() {
        var postData = ""
        postData += "user_id=$userId"
        postData += "&challenge_id=$challengeId"
        postData += "&paid_amount=100"
        postData += "&rewards_amount=0"
        postData += "&pay_method=card"
        postData += "&pg=nice"
        val url = Routes.PAYMENT_HOST_NAME.dropLast(1) + Routes.REQUEST_PAYMENT
        Timber.e("url : $url, postData : $postData")
        binding.webView.postUrl(url, postData.toByteArray())
    }
}