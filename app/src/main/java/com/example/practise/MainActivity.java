package com.example.practise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.practise.counter.CounterActivity;
import com.example.practise.recyclerview.RecyclerViewActivity;
import com.example.practise.rememberpwd.LoginActivity;
import com.example.practise.sqlitedemo.SqliteDemoActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/26.
 */

public class MainActivity extends AppCompatActivity {

    private Button btnAct, btnRv, btnCounter, btnLogin, btnSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAct = findViewById(R.id.btn_act);

        btnRv = findViewById(R.id.btn_rv);

        btnCounter = findViewById(R.id.btn_counter);

        btnLogin = findViewById(R.id.btn_login);

        btnSqlite = findViewById(R.id.btn_sqlite);

        btnAct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,  NewActivity.class);
                startActivity(intent);
//                Log.d("1", "测试");
            }
        });

        btnRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,  RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        btnCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CounterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SqliteDemoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}