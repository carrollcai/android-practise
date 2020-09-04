package com.example.practise.webview;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.practise.R;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewJSActivity extends AppCompatActivity {

    private WebView mWebView;
    private Button mButton;
    public TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_j_s);

        mWebView = findViewById(R.id.webview_js);
        mButton = findViewById(R.id.webview_call_js);
        mTextView = findViewById(R.id.webview_tv);

        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        Android2Js android2Js = new Android2Js(WebViewJSActivity.this);

        mWebView.addJavascriptInterface(android2Js, "jsBridge");

        mWebView.loadUrl("file:///android_asset/www/index-js.html");

//        mWebView.loadUrl("file:///android_asset/www/index.html");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 通过handle发送信息
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:callJs(\"消息来自安卓\")");
                    }
                });
            }
        });
    }
}