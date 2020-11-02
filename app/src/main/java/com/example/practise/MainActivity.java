package com.example.practise;

import android.content.Intent;
import android.os.Bundle;

import com.example.practise.HttpURLConnectionDemo.HttpURLConnectionActivity;
import com.example.practise.contentproviderdemo.ContentProviderDemo;
import com.example.practise.counter.CounterActivity;
import com.example.practise.customview.CustomViewActivity;
import com.example.practise.eventbus.EventBusActivity;
import com.example.practise.horizontalview.HorizontalViewActivity;
import com.example.practise.mvp.ipinfo.MVPActivity;
import com.example.practise.rectview.RectViewActivity;
import com.example.practise.recyclerview.RecyclerViewActivity;
import com.example.practise.rememberpwd.LoginActivity;
import com.example.practise.servicebest.DownloadDemoActivity;
import com.example.practise.sqlitedemo.SqliteDemoActivity;
import com.example.practise.taglayout.TagLayoutActivity;
import com.example.practise.titlebar.TitleBarActivity;
import com.example.practise.webview.WebViewJSActivity;
import com.example.practise.webview.WebviewDemoActivity;
import com.example.practise.webview.WebviewLoadLocalHtmlActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/26.
 */

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 访问网络
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());

        ArrayList<BtnList> list = new ArrayList();
        list.add(new BtnList("打开RecyclerView", RecyclerViewActivity.class));
        list.add(new BtnList("打开计数器", CounterActivity.class));
        list.add(new BtnList("打开登录", LoginActivity.class));
        list.add(new BtnList("打开sqlite", SqliteDemoActivity.class));
        list.add(new BtnList("打开ContentProvider", ContentProviderDemo.class));
        list.add(new BtnList("打开HttpURLConnection", HttpURLConnectionActivity.class));
        list.add(new BtnList("打开Webview", WebviewDemoActivity.class));
        list.add(new BtnList("打开Webview本地HTML", WebviewLoadLocalHtmlActivity.class));
        list.add(new BtnList("打开Webview JS", WebViewJSActivity.class));
        list.add(new BtnList("打开自定义View", CustomViewActivity.class));
        list.add(new BtnList("打开下载服务", DownloadDemoActivity.class));
        list.add(new BtnList("打开TagLayout", TagLayoutActivity.class));
        list.add(new BtnList("打开MVPActivity", MVPActivity.class));
        list.add(new BtnList("打开RectViewActivity", RectViewActivity.class));
        list.add(new BtnList("打开TitleBar", TitleBarActivity.class));
        list.add(new BtnList("打开Horizontal Demo", HorizontalViewActivity.class));
        list.add(new BtnList("打开EventBus", EventBusActivity.class));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        MainAdapter adapter = new MainAdapter(list, new MainAdapter.OnItemClickListener() {
            @Override
            public void onClick(Class<?> cls) {
                Intent intent = new Intent(MainActivity.this, cls);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}