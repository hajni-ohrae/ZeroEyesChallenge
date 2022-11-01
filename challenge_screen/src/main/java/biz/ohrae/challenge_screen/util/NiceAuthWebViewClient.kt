package biz.ohrae.challenge_screen.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import java.net.URISyntaxException


class NiceAuthWebViewClient(private val context: Context): WebViewClient() {
    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.shouldOverrideUrlLoading(view, url)",
        "android.webkit.WebViewClient"
    )
    )
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        //웹뷰 내 표준창에서 외부앱(통신사 인증앱)을 호출하려면 intent:// URI를 별도로 처리해줘야 합니다.
        //다음 소스를 적용 해주세요.
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

}