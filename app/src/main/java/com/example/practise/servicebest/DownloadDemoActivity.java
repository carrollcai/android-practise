package com.example.practise.servicebest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.practise.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DownloadDemoActivity extends AppCompatActivity {

    private DownloadService.DownloadBinder mDownloadBinder;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_demo);

        Button startDownload = findViewById(R.id.start_download);
        Button pauseDownload = findViewById(R.id.pause_download);
        Button cancelDownload = findViewById(R.id.cancel_download);

        startDownload.setOnClickListener(new StartDownloadClick());
        pauseDownload.setOnClickListener(new PauseDownloadClick());
        cancelDownload.setOnClickListener(new CancelDownloadClick());

        Intent intent = new Intent(this, DownloadService.class);

        // 启动服务
        startService(intent);

        // 绑定服务。为了能调用mDownloadBinder对象。
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        if (ContextCompat.checkSelfPermission(DownloadDemoActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadDemoActivity.this, PERMISSIONS_STORAGE, 1);
        }
    }

    private class StartDownloadClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("测试", "点击开始");
            String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
            mDownloadBinder.startDownload(url);
        }
    }

    private class PauseDownloadClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mDownloadBinder.pauseDownload();
        }
    }

    private class CancelDownloadClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mDownloadBinder.cancelDownload();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        Log.d("DownloadDemoActivity", "onDestroy");
        super.onDestroy();
        unbindService(mConnection);
    }
}