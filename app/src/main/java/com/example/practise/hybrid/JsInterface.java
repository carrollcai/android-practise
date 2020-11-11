package com.example.practise.hybrid;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.example.practise.webview.WebViewJSActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class JsInterface extends Object {
    private String TAG = "JsInterface";
    private AppCompatActivity webViewJSActivity;
    private Message mMessage;
    private WebView mWebView;
    private String jsUrl = "javascript: window.jsBridge._handleMessageFromNative";

    public JsInterface(AppCompatActivity webViewJSActivity, WebView mWebView, Message message) {
        this.webViewJSActivity = webViewJSActivity;
        this.mMessage = message;
        this.mWebView = mWebView;
    }

    @JavascriptInterface
    public void receiveJsAndEvaluateCallback(String str, String callbackName) {
        mMessage.add(str, callbackName);

        mWebView.post(new Runnable() {
            @Override
            public void run() {
                String name = mMessage.get(str);
                JSONObject object = new JSONObject();
                try {
                    object.put("functionName", name);
                    object.put("data", "来自安卓的消息");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 安卓的JSONObject对象再传给前端的时候，不需要序列化
                mWebView.evaluateJavascript(jsUrl + "(" + object.toString() + ")", null);
            }
        });
    }
}
