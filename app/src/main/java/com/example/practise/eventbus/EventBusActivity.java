package com.example.practise.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practise.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);

        tv = findViewById(R.id.event_tv);
    }

    @OnClick(R.id.register) void OnClickRegister() {
        if (!EventBus.getDefault().isRegistered(EventBusActivity.this)) {
            EventBus.getDefault().register(EventBusActivity.this);
        } else {
            Toast.makeText(EventBusActivity.this, "请勿重复注册", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.startActivity) void startActivityClick() {
        Intent intent = new Intent(EventBusActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(MessageEvent messageEvent) {
        tv.setText(messageEvent.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}