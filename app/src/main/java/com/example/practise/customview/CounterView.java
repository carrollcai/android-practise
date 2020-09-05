package com.example.practise.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.practise.R;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/9/5.
 */


public class CounterView extends LinearLayout {
//    private View view;
    private TextView mTextView;

    public CounterView(Context context) {
        super(context);
    }

    public CounterView(Context context,  AttributeSet attrs) {
        super(context, attrs);

        // 自定义组件注册
        LayoutInflater.from(context).inflate(R.layout.custom_counter_view, this);

        // 获取当前视图的实例，可操作视图
//        view = inflate(getContext(), R.layout.custom_counter_view, this);
        mTextView = findViewById(R.id.custom_text);
    }

    public void setTextValue(String s) {
        Log.d("值", s);
        mTextView.setText(s);
    }

}
