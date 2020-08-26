package com.example.practise.counter;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import static com.example.practise.counter.CounterActivity.DATE_PATTERN;
import static com.example.practise.counter.CounterActivity.TIME_CHANGE_ACTION;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/26.
 */


public class TimeService extends Service {

    private Timer timer;
    private Intent timeIntent = null;
    private Bundle bundle = null;
    private SimpleDateFormat sdf = null;
    private boolean flag = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TimerService","----onCreate----");

        init();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (flag) {
                    sendTimeChangedBroadcast();
                } else {
                    timer.cancel();
                    timer.purge();
                    timer = null;
                }
            }
        }, 200, 1000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TimerService","----onBind----");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("TimerService","----onUnbind----");
        return super.onUnbind(intent);
    }

    private void init() {
        timer = new Timer();
        sdf = new SimpleDateFormat(DATE_PATTERN);
        timeIntent = new Intent();
        bundle = new Bundle();
    }

    private void sendTimeChangedBroadcast() {
        bundle.putString("time", getTime());
        timeIntent.putExtras(bundle);
        timeIntent.setAction(TIME_CHANGE_ACTION);

        sendBroadcast(timeIntent);

    }

    private String getTime() {
        return sdf.format(new Date());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TimerService","----onDestroy----");
        flag = false;
    }
}
