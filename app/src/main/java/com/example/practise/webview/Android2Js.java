package com.example.practise.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/9/4.
 */


public class Android2Js extends Object {

    private WebViewJSActivity webViewJSActivity;

    public Android2Js(WebViewJSActivity webViewJSActivity) {
        this.webViewJSActivity = webViewJSActivity;
    }

    @JavascriptInterface
    public void receiveJs(String str) {
        Log.d("receiveJs", str);

        webViewJSActivity.mTextView.setText(str);
    }
}
