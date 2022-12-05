package biz.ohrae.challenge_screen.ui.payment.iamport;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import biz.ohrae.challenge_screen.ui.niceid.NiceIdActivity;
import biz.ohrae.challenge_screen.ui.payment.ChallengePaymentActivity;

public class Bridge {
    Context context;
    WebView webView;

    public Bridge(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }

    @JavascriptInterface
    public void paymentResult(boolean isSuccess, String code, String message, String amount, String cardName, String rewardsAmount) {
        ((ChallengePaymentActivity) context).paymentResult(isSuccess, code, message, amount, cardName, rewardsAmount);
    }

    @JavascriptInterface
    public void checkPlusClose() {
        ((NiceIdActivity) context).close();
    }
}
