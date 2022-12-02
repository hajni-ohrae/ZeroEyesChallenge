@file:Suppress("OverrideDeprecatedMigration")

package biz.ohrae.challenge_screen.ui.terms

import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import biz.ohrae.challenge_screen.databinding.ActivityTermsWebviewBinding
import biz.ohrae.challenge_screen.util.NiceAuthWebViewClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsWebViewActivity : AppCompatActivity() {
    companion object {
        private const val CHALLENGE_TERMS = "https://zeroeyes.notion.site/04de8b09fa154c89b04489911ad95f76"
        private const val CHALLENGE_PRIVACY = "https://zeroeyes.notion.site/facb47c1c05847d3867b15bed3bdbc01"
        private const val CHALLENGE_CAUTION = "https://zeroeyes.notion.site/def3b47f4c4e4128be58023a7042aaba"
        private const val CHALLENGE_REWARD = "https://zeroeyes.notion.site/aa8044e249494874a03fc75fc3425e68"
        private const val CHALLENGE_RED_CARD = "https://zeroeyes.notion.site/554ec38416864ad3982c949890a8b3e8"

        val TERMS_MAP = mapOf<String, String>(
            Pair("terms", CHALLENGE_TERMS),
            Pair("privacy", CHALLENGE_PRIVACY),
            Pair("caution", CHALLENGE_CAUTION),
            Pair("reward", CHALLENGE_REWARD),
            Pair("redCard", CHALLENGE_RED_CARD),
        )
    }

    private lateinit var binding: ActivityTermsWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val type = intent.getStringExtra("type")
        init(type)
    }

    private fun init(type: String?) {
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
        webView.webViewClient = NiceAuthWebViewClient(this@TermsWebViewActivity)
        webView.webChromeClient = WebChromeClient()

        val url = TERMS_MAP[type]
        if (url != null) {
            webView.loadUrl(url)
        }
    }
}