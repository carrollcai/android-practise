package com.example.practise.contentproviderdemo;

import android.os.Bundle;

import com.example.practise.R;

import androidx.appcompat.app.AppCompatActivity;

// ContentResolver需要在另外一个应用上实现。

public class ContentProviderDemo extends AppCompatActivity {

    public DBManager mDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);
        mDBManager = new DBManager(ContentProviderDemo.this);
    }
}