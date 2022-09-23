package biz.ohrae.challenge_screen.ui.payment.iamport;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by jang on 2017. 9. 9..
 */

public class NiceWebViewClient extends WebViewClient {
    private AppCompatActivity activity;
    private WebView target;
    private String BANK_TID = "";
    private String NICE_BANK_URL = "https://web.nicepay.co.kr/smart/bank/payTrans.jdp";    // 계좌이체 거래 요청 URL(V2부터는 가변적일 수 있음)

    final int RESCODE = 1;
    final String NICE_URL = "https://web.nicepay.co.kr/smart/interfaceURL.jdp";            // NICEPAY SMART 요청 URL
    final String KTFC_PACKAGE = "com.kftc.bankpay.android";

    public NiceWebViewClient(AppCompatActivity activity, WebView target) {
        this.activity = activity;
        this.target = target;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
            Intent intent = null;

            try {
                if (url.startsWith(PaymentScheme.BANKPAY)) {
                    /* START - BankPay(실시간계좌이체)에 대해서는 예외적으로 처리 */
                    String backpayPackageName = "com.kftc.bankpay.android";
                    String backpayClassName = "com.kftc.bankpay.android.activity.MainActivity";
                    if (isInstallApp(backpayPackageName)) {
                        // IMP.request_pay(param) 호출 시 param.niceMobileV2 : true인 경우에는 makeBankPayData(url) 대신 makeBankPayDataV2(url); 을 호출해주세요
                        // String reqParam = makeBankPayData(url);
                        String reqParam = makeBankPayDataV2(url);

                        intent = new Intent(Intent.ACTION_MAIN);
                        intent.setComponent(new ComponentName(backpayPackageName, backpayClassName));
                        intent.putExtra("requestInfo", reqParam);
                        activity.startActivityForResult(intent, RESCODE);

                        return true;
                    } else {
                        handleNotFoundPaymentScheme(PaymentScheme.BANKPAY);
                        return true;
                    }
                    /* END - BankPay(실시간계좌이체)에 대해서는 예외적으로 처리 */
                } else {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
                    Uri uri = Uri.parse(intent.getDataString());

                    activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    return true;
                }
            } catch (URISyntaxException ex) {
                return false;
            } catch (UnsupportedEncodingException uee) {
                return false;
            } catch (ActivityNotFoundException e) {
                if (intent == null) return false;

                if (handleNotFoundPaymentScheme(intent.getScheme())) return true;

                String packageName = intent.getPackage();
                if (packageName != null) {
                    try {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    } catch (Exception ee) {
                        Snackbar.make(activity, activity.getWindow().getDecorView(), "카카오톡 앱을 먼저 설치하세요.", Snackbar.LENGTH_SHORT).show();
                    }
                    return true;
                }

                return false;
            }
        }

        return false;
    }

    private boolean isInstallApp(String pakageName) {
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(pakageName);

        if (intent == null) {
            //미설치
            return false;
        } else {
            //설치
            return true;
        }
    }

    /**
     * @param scheme
     * @return 해당 scheme에 대해 처리를 직접 하는지 여부
     * <p>
     * 결제를 위한 3rd-party 앱이 아직 설치되어있지 않아 ActivityNotFoundException이 발생하는 경우 처리합니다.
     * 여기서 handler되지않은 scheme에 대해서는 intent로부터 Package정보 추출이 가능하다면 다음에서 packageName으로 market이동합니다.
     */
    protected boolean handleNotFoundPaymentScheme(String scheme) {
        //PG사에서 호출하는 url에 package정보가 없어 ActivityNotFoundException이 난 후 market 실행이 안되는 경우
        if (PaymentScheme.ISP.equalsIgnoreCase(scheme)) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_ISP)));
            return true;
        } else if (PaymentScheme.BANKPAY.equalsIgnoreCase(scheme)) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_BANKPAY)));
            return true;
        }

        return false;
    }

    public void bankPayPostProcess(String bankpayCode, String bankpayValue) {
        try {
            String postData = "callbackparam2=" + BANK_TID + "&bankpay_code=" + URLEncoder.encode(bankpayCode, "euc-kr") + "&bankpay_value=" + URLEncoder.encode(bankpayValue, "euc-kr");
            target.postUrl(NICE_BANK_URL, postData.getBytes());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String makeBankPayData(String url) throws URISyntaxException {
        BANK_TID = "";
        Uri uri = Uri.parse(url);
        Set<String> queryNames = uri.getQueryParameterNames();

        StringBuilder ret_data = new StringBuilder();
        List<String> keys = Arrays.asList(new String[]{"firm_name", "amount", "serial_no", "approve_no", "receipt_yn", "user_key", "callbackparam2", ""});

        String v;
        for (String k : queryNames) {

            if (keys.contains(k)) {
                v = uri.getQueryParameter(k);

                if ("user_key".equals(k)) {
                    BANK_TID = v;
                }
                ret_data.append("&").append(k).append("=").append(v);
            }
        }

        ret_data.append("&callbackparam1=" + "nothing");
        ret_data.append("&callbackparam3=" + "nothing");

        return ret_data.toString();
    }

    private String makeBankPayDataV2(String url) throws URISyntaxException, UnsupportedEncodingException {
        String prefix = PaymentScheme.BANKPAY + "://eftpay?";

        Uri uri = Uri.parse(url);
        BANK_TID = uri.getQueryParameter("user_key");
        NICE_BANK_URL = uri.getQueryParameter("callbackparam1");

        return URLDecoder.decode(url.substring(prefix.length()), "utf-8");
    }

}