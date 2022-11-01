package biz.ohrae.challenge_screen.util

import android.app.Dialog
import android.content.Context
import android.os.Message
import android.view.ViewGroup
import android.webkit.*

class NiceAuthChromeClient(private val context: Context): WebChromeClient() {
    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        val newWebView = WebView(context)
        val settings = newWebView.settings

        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.setSupportMultipleWindows(true)
        settings.javaScriptCanOpenWindowsAutomatically = true

        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = true
        settings.setSupportZoom(true)
        settings.loadWithOverviewMode = true

        val dialog = Dialog(context).apply {
            setContentView(newWebView)
            window!!.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
            window!!.attributes.height = ViewGroup.LayoutParams.MATCH_PARENT
            show()
        }

        newWebView.webChromeClient = object : WebChromeClient() {
            override fun onCloseWindow(window: WebView?) {
                dialog.dismiss()
            }
        }

        newWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }

        (resultMsg?.obj as WebView.WebViewTransport).webView = newWebView
        resultMsg.sendToTarget()
        return true
    }
}