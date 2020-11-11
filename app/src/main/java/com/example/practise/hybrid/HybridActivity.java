package com.example.practise.hybrid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.practise.R;
import com.example.practise.webview.Android2Js;
import com.example.practise.webview.WebViewJSActivity;

public class HybridActivity extends AppCompatActivity {

    private WebView mWebView;
    private Message mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);
        initWebView();
    }

    private void initWebView() {
        mMessage = new Message();

        mWebView = findViewById(R.id.webview_js);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        JsInterface jsInterface = new JsInterface(HybridActivity.this, mWebView, mMessage);
        mWebView.addJavascriptInterface(jsInterface, "jsBridge");

        // jsBridge的注入最好由前端注入。当然客户端也可以通过onPageStarted注入，如果没注入成功，在onPageFinished的再注入一次。
        mWebView.setWebViewClient(new WebViewClient() {
            boolean hasInjected = false;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!hasInjected) {
                    mWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("onPageStarted", "onPageStarted");
//                            mWebView.evaluateJavascript("file:///android_asset/www/jsBridge.js", null);
                            hasInjected = true;
                        }
                    });
                }
            }
        });

        mWebView.loadUrl("file:///android_asset/www/hybrid.html");
    }
}