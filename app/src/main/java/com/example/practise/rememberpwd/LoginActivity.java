package com.example.practise.rememberpwd;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.example.practise.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/26.
 */

public class LoginActivity extends AppCompatActivity {

    private Button mSubmit;
    private EditText mAccount, mPassword;
    private CheckBox mCheckBox;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences sp;
    private AlertDialog prompt;
    private Timer timer;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAccount = findViewById(R.id.login_account);
        mPassword = findViewById(R.id.login_pwd);
        mSubmit = findViewById(R.id.login_btn);
        mCheckBox = findViewById(R.id.login_cb);

        timer = new Timer();

        sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);

        String cacheAccount = sp.getString("account", "");
        String cachePassword = sp.getString("password", "");

        if (cacheAccount != "" && cachePassword != "") {
            mCheckBox.setChecked(true);
            mAccount.setText(cacheAccount);
            mPassword.setText(cachePassword);
        }

        mEditor = sp.edit();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mAccount.getText().toString();
                String pwd = mPassword.getText().toString();

                if (account == "" || pwd == "") {
                    Toast.makeText(LoginActivity.this, "账号密码不能为空", Toast.LENGTH_LONG).show();
                } else {

                    prompt = prompt(LoginActivity.this, "正在跳转");

                    prompt.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (prompt.isShowing()) {
                                prompt.dismiss();
                            }
                        }
                    });

                    if (mCheckBox.isChecked()) {
                        mEditor.putString("account", account);
                        mEditor.putString("password", pwd);
                    } else {
                        mEditor.remove("account");
                        mEditor.remove("password");
                    }
                    mEditor.apply();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (flag) {
                                if (prompt.isShowing()) {
                                    prompt.dismiss();
                                }
                                timer.cancel();
                                timer.purge();
                                timer = null;

                                flag = false;

                                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                                startActivity(intent);

                            }
                        }
                    }, 3000, 5000);
//                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
//                    startActivity(intent);

                }
            }
        });


        findViewById(R.id.login_test1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("onTouch", "onTouch setMotionEventSplittingEnabled1，" + event);
                return true;
            }
        });

        findViewById(R.id.login_test2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("onTouch", "onTouch setMotionEventSplittingEnabled2，" + event);
                return true;
            }
        });

        mAccount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("onTouch", "onTouch setMotionEventSplittingEnabled3，" + event);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        flag = true;
    }

    private AlertDialog prompt(LoginActivity loginActivity, String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity);
        LinearLayout layout = (LinearLayout) loginActivity.getLayoutInflater().inflate(R.layout.prompt, null);

        ((TextView) layout.findViewById(R.id.message)).setText(str);
        builder.setView(layout);
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}