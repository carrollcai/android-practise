package com.example.practise.sqlitedemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.practise.R;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTabHost;

public class SqliteDemoActivity extends FragmentActivity {
    private FragmentTabHost tabHost;
    private LayoutInflater layoutInflater;
    // 利用了Java的反射机制
    private Class<?> fragmentArr[] = {CameraHistoryFragment.class, CameraHistoryFragment.class, CameraHistoryFragment.class};
    private int tabImageArr[] = {R.drawable.camera_tab_btn, R.drawable.audio_tab_btn, R.drawable.setting_tab_btn};
    private String tabTextArr[] = {"Camera", "Audio", "Setting"};
    public static DBManager dbManger;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_demo);

        dbManger = new DBManager(SqliteDemoActivity.this);

        initView();

        // 模拟器下，不用动态申请权限，这里只是展示一下如何动态申请权限。动态申请权限应该在图片被调用时才申请，不应该一进来就申请。
        verifyStoragePermissions(SqliteDemoActivity.this);
    }


    // 动态申请权限
    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(this);
        tabHost = findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        int count = fragmentArr.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTextArr[i]).setIndicator(getTabItemView(i));
            tabHost.addTab(tabSpec, fragmentArr[i], null);

            tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    private View getTabItemView(int i) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = view.findViewById(R.id.imageview);
        imageView.setImageResource(tabImageArr[i]);
        return view;
    }
}