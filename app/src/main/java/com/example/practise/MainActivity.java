package com.example.practise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.practise.HttpURLConnectionDemo.HttpURLConnectionActivity;
import com.example.practise.contentproviderdemo.ContentProviderDemo;
import com.example.practise.customview.CustomViewActivity;
import com.example.practise.horizontalview.HorizontalView;
import com.example.practise.horizontalview.HorizontalViewActivity;
import com.example.practise.mvp.ipinfo.MVPActivity;
import com.example.practise.rectview.RectView;
import com.example.practise.rectview.RectViewActivity;
import com.example.practise.servicebest.DownloadDemoActivity;
import com.example.practise.sqlitedemo.SqliteDemoActivity;
import com.example.practise.taglayout.TagLayoutActivity;
import com.example.practise.titlebar.TitleBarActivity;
import com.example.practise.webview.WebViewJSActivity;
import com.example.practise.webview.WebviewDemoActivity;
import com.example.practise.webview.WebviewLoadLocalHtmlActivity;

import androidx.appcompat.app.AppCompatActivity;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/26.
 */

public class MainActivity extends AppCompatActivity {

    private Button btnRv, btnCounter, btnLogin, btnSqlite,
            btnContentProvider, btnHttpUrl, btnWebview,
            btnWebviewHtml, btnWebviewJS, btnCustomView,
            btnDownloadService, btnMVP, btnTagLayout, btnRectView,
            btnTitleBar, btnHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 访问网络
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());

//        btnRv = findViewById(R.id.btn_rv);
//
//        btnCounter = findViewById(R.id.btn_counter);
//
//        btnLogin = findViewById(R.id.btn_login);

        btnSqlite = findViewById(R.id.btn_sqlite);

        btnSqlite = findViewById(R.id.btn_sqlite);

        btnContentProvider = findViewById(R.id.btn_contentprovider);

//        btnHttpUrl = findViewById(R.id.btn_httpurl);
//
//        btnWebview = findViewById(R.id.btn_webview);
//
//        btnWebviewHtml = findViewById(R.id.btn_webview_html);

        btnWebviewJS = findViewById(R.id.btn_webview_js);

        btnCustomView = findViewById(R.id.btn_custom_view);

        btnDownloadService = findViewById(R.id.btn_download_service);

        btnMVP = findViewById(R.id.btn_mvp);

//        btnTagLayout = findViewById(R.id.btn_tag_layout);

        btnRectView = findViewById(R.id.btn_rect_view);

        btnTitleBar = findViewById(R.id.btn_title_bar);

        btnHorizontal = findViewById(R.id.btn_horizontal);

//        btnRv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,  RecyclerViewActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnCounter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CounterActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        btnSqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SqliteDemoActivity.class);
                startActivity(intent);
            }
        });

        btnContentProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContentProviderDemo.class);
                startActivity(intent);
            }
        });

//        btnHttpUrl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, HttpURLConnectionActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnWebview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, WebviewDemoActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnWebviewHtml.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, WebviewLoadLocalHtmlActivity.class);
//                startActivity(intent);
//            }
//        });

        btnWebviewJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewJSActivity.class);
                startActivity(intent);
            }
        });

        btnCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomViewActivity.class);
                startActivity(intent);
            }
        });

        btnDownloadService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadDemoActivity.class);
                startActivity(intent);
            }
        });

        btnMVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MVPActivity.class);
                startActivity(intent);
            }
        });

//        btnTagLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, TagLayoutActivity.class);
//                startActivity(intent);
//            }
//        });

        btnRectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RectViewActivity.class);
                startActivity(intent);
            }
        });

        btnTitleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TitleBarActivity.class);
                startActivity(intent);
            }
        });

        btnHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HorizontalViewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}