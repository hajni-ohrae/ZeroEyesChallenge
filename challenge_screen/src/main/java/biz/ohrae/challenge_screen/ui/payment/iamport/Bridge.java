package biz.ohrae.challenge_screen.ui.payment.iamport;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class Bridge {
    Context context;
    WebView webView;

    public Bridge(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }

    @JavascriptInterface
    public void paymentResult(boolean isSuccess, String code, String message) {
//        ((NicePayActivity) context).paymentResult(isSuccess, code, message);
    }
}
