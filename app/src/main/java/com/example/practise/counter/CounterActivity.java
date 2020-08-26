package com.example.practise.counter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.practise.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/26.
 */

public class CounterActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mEditText;

    public static String TIME_CHANGE_ACTION = "TIME_CHANGE_ACTION";
    public static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private DateFormat df = new SimpleDateFormat(DATE_PATTERN);
    private Date startDate = null, currentDate = null;
    private Intent serviceIntent = null;
    private TimeReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        mButton = findViewById(R.id.counter_btn);
        mEditText = findViewById(R.id.counter_et);


        initWidget();

        registerBroadcastReceiver();
    }

    private void initWidget() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", mButton.getText().toString().equals("开始计时") + "");
                if (mButton.getText().toString().equals("开始计时")) {
                    // 启动广播，时间改变后通知UI层修改

                    try {
                        String nowDateStr = df.format(new Date());
                        startDate = df.parse(nowDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    startTimeService();
                    mButton.setText("停止计时");
                } else {
                    stopService(serviceIntent);
                    serviceIntent = null;
                    startDate = null;
                    currentDate = null;
                    mEditText.setText("");
                    mButton.setText("开始计时");
                }
            }
        });
    }

    private void startTimeService() {
        serviceIntent = new Intent(this, TimeService.class);
        this.startService(serviceIntent);
    }

    private  void  registerBroadcastReceiver() {
        Log.d("", "接收到返回");
        receiver = new TimeReceiver();
        IntentFilter filter = new IntentFilter(TIME_CHANGE_ACTION);
        registerReceiver(receiver, filter);
    }

    public class TimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action != null && TIME_CHANGE_ACTION.equals(action)) {
                String currentTime = intent.getExtras().getString("time");

                try {
                    currentDate = df.parse(currentTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long diff = currentDate.getTime() - startDate.getTime();

                long days = diff / (1000 * 60 * 60 * 24);
                long hours = diff / (1000 * 60 * 60) - days * 24;
                long mins = (diff / (60 * 1000)) - days * 24 * 60 - hours * 60;
                long s = diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - mins * 60;
                String diffTimeStr = days + "天" + hours + "小时" + mins + "分" + s + "秒";
                mEditText.setText(diffTimeStr);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        if (serviceIntent != null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }
}