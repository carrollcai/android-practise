package com.example.practise.customview;

import android.os.Bundle;

import com.example.practise.R;

import androidx.appcompat.app.AppCompatActivity;

public class CustomViewActivity extends AppCompatActivity {
    private CounterView mCounterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        mCounterView = findViewById(R.id.custom_counterview);

        mCounterView.setTextValue("传值到自定义组件成功");
    }
}