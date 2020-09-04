package com.example.practise.webview;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.practise.R;

import androidx.appcompat.app.AppCompatActivity;

public class WebviewLoadLocalHtmlActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_load_local_html);

        mWebView = findViewById(R.id.webview_html);
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);

        for (int i = 0; i < 10; i++) {
//获取当前i的值
            int selector = i;
            //打log查看当前i的值（此步多余，实际开发请忽略）
//            Logger.e("for当前的i的值：" + i);
            //调用方法
            stepNext(i);
        }


        // 加载本地html文件
        mWebView.loadUrl("file:///android_asset/www/index.html");

        // 加载远程数据或者字符串
        //        String data = "<html><body>你好</body></html>";
//        mWebView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", "");
    }

    private void stepNext(int i) {
        Log.d("索引值", i + "");
    }
}