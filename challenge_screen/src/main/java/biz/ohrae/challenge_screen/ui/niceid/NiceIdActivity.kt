@file:Suppress("OverrideDeprecatedMigration")

package biz.ohrae.challenge_screen.ui.niceid

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import biz.ohrae.challenge_repo.data.remote.Routes
import biz.ohrae.challenge_screen.databinding.ActivityNiceIdBinding
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeViewModel
import biz.ohrae.challenge_screen.ui.payment.iamport.Bridge
import biz.ohrae.challenge_screen.util.NiceAuthWebViewClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NiceIdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNiceIdBinding
    private lateinit var myChallengeViewModel: MyChallengeViewModel

    private val APP_SCHEME = "mooinapp://"

    private var userId: String? = null
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            close()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNiceIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId")
        myChallengeViewModel = ViewModelProvider(this)[MyChallengeViewModel::class.java]

        onBackPressedDispatcher.addCallback(this, callback)

        init()
    }

    private fun init() {
        val webView = binding.webView
        val settings: WebSettings = webView.settings

        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.domStorageEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(webView, true)
        }
        webView.webViewClient = NiceAuthWebViewClient(this@NiceIdActivity)
        webView.webChromeClient = WebChromeClient()
        val bridge = Bridge(this, webView)
        webView.addJavascriptInterface(bridge, "Bridge")
        val intent = intent
        val intentData = intent.data

        val url = "${Routes.HOST_NAME.dropLast(1)}/views/checkplus/main/$userId"
        webView.loadUrl(url)
    }

    fun close() {
        myChallengeViewModel.getUserData()
        val intent = Intent()
        intent.putExtra("done", true)
        setResult(RESULT_OK, intent)
        finish()
    }
}