package com.example.practise.startdraganddrop;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practise.R;

import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.appcompat.app.AlertDialog;

public class StartDargAndDropActivity extends AppCompatActivity {

    private TextView mTextView;
    private View.OnLongClickListener mLongClickListener;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_darg_and_drop);


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("测试标题1");
        builder.setMessage("测试内容1");
        builder.setPositiveButton("知道了", null);
        mDialog = builder.create();
        mDialog.show();

        mLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(data, shadowBuilder, v, View.DRAG_FLAG_OPAQUE);
                return false;
            }
        };

        mTextView = (TextView) findViewById(R.id.text);
        mTextView.setOnLongClickListener(mLongClickListener);
        bindFolme(mTextView);
        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTextView.setAlpha(0.3f);
                return false;
            }
        });
    }

    private void bindFolme(View v) {
        IFolme mc = Folme.useAt(v);

        int tintColor = v.getResources().getColor(R.color.table_view_head_row);
        int alpha = (0xff000000 & tintColor) >>> 24;
        int red = (0xff0000 & tintColor) >>> 16;
        int green = (0xff00 & tintColor) >>> 8;
        int blue = 0xff & tintColor;
        mc.touch().setTint(alpha / 255f, red / 255f, green / 255f, blue / 255f);
        v.setTag(R.id.tag_folme, mc);
    }

    private void onMotionEvent(View v, MotionEvent event) {
        Object mc = v.getTag(R.id.tag_folme);
        if (mc instanceof IFolme) {
            ((IFolme) mc).touch().onMotionEventEx(v, event);
        }
    }
}