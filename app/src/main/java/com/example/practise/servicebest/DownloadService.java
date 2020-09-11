package com.example.practise.servicebest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import com.example.practise.R;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/9/10.
 */


public class DownloadService extends Service {

    private DownloadTask mDownloadTask;

    private String downloadUrl;

    private IDownloadListener mDownloadListener = new IDownloadListener() {
        @Override
        public void onProgress(int progress) {
            Log.d("DownloadService progress",progress + "");

            // 不知道为什么这里不直接更新前台的视图
            getNotificationManager().notify(1, startMyOwnForeground("Downloading...", progress));

            // 这里必须通知前台视图更新
            startForeground(1, startMyOwnForeground("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            mDownloadTask = null;

            Log.d("", "onSuccess");

            stopForeground(true);


            getNotificationManager().notify(1, startMyOwnForeground("Download Success", -1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailed() {
            mDownloadTask = null;

            Log.d("", "onFailed");

            stopForeground(true);

            getNotificationManager().notify(1, startMyOwnForeground("Download Failed", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            mDownloadTask = null;

            Toast.makeText(DownloadService.this, "Download Paused", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCanceled() {
            mDownloadTask = null;

            stopForeground(true);

            Toast.makeText(DownloadService.this, "Download Canceled", Toast.LENGTH_LONG).show();
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("IBinder Success", "");
        return mBinder;
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }


    // 为了和活动通信
    public class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (mDownloadTask == null) {
                downloadUrl = url;
                Log.d("", "结果:mDownloadListener");
                mDownloadTask = new DownloadTask(mDownloadListener);
                mDownloadTask.execute(downloadUrl);

                startForeground(1, startMyOwnForeground("Downloading", 0));

                Toast.makeText(DownloadService.this, "Downloading", Toast.LENGTH_LONG).show();

            }
        }

        public void pauseDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.pauseDownload();
            }

            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_LONG).show();
        }

        public void cancelDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.cancelDownload();
            }

            if (downloadUrl != null) {
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

                File file = new File(fileName + directory);
                if (file.exists()) {
                    file.delete();
                }

                getNotificationManager().cancel(1);
                stopForeground(true);

                Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_LONG).show();

            }
        }
    }


    // 发现视图不更新的问题
    private  Notification startMyOwnForeground(String title, int progress) {
        Log.d("startMyOwnForeground", progress + "");

        String NOTIFICATION_CHANNEL_ID = "com.example.practise";

        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(chan);

        Intent intent = new Intent(this, DownloadDemoActivity.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setOngoing(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setPriority(NotificationManager.IMPORTANCE_DEFAULT);
        builder.setCategory(Notification.CATEGORY_SERVICE);
        builder.setContentTitle(title);

        if (progress >= 0) {
            Log.d("startMyOwnForeground progress inter", progress + "");
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress ,false);
        }

        return builder.build();
    }
}
