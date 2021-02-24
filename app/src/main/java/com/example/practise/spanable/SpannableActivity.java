package com.example.practise.spanable;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.widget.TextView;

import com.example.practise.R;

import miuix.animation.Folme;
import miuix.animation.IFolme;

public class SpannableActivity extends AppCompatActivity {
    private String CONTENT = "ActionBar";
    private static String TAG = "SpannableActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);

        TextView mTvTextView = findViewById(R.id.span_tv);

        MutableForegroundColorSpan span = new MutableForegroundColorSpan(255, Color.BLUE);
        final SpannableString spannableString = new SpannableString(CONTENT);
        spannableString.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_FOREGROUND_COLOR_SPAN_PROPERTY, Color.BLACK, Color.RED);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //refresh
                mTvTextView.setText(spannableString);
            }
        });
//        objectAnimator.setInterpolator(mSmoothInterpolator);
        objectAnimator.setDuration(600);
        objectAnimator.start();

        findViewById(R.id.span_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String random = String.valueOf(((int)(Math.random() * 100) % 10));
                MutableForegroundColorSpan span = new MutableForegroundColorSpan(255, Color.BLUE);

                int length = mTvTextView.getText().length();

                String newStr = mTvTextView.getText() + random;
                SpannableString spannableString = new SpannableString(newStr);
                spannableString.setSpan(span, length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                spannableString.setSpan(new AbsoluteSizeSpan(sp2px(15)), length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_FOREGROUND_COLOR_SPAN_PROPERTY, Color.WHITE, Color.RED);



                objectAnimator.setEvaluator(new TypeEvaluator() {

                    @Override
                    public Object evaluate(float fraction, Object startValue, Object endValue) {


//                        int startInt = (Integer) startValue;
//                        int startA = (startInt >> 24);
//                        int startR = (startInt >> 16) & 0xff;
//                        int startG = (startInt >> 8) & 0xff;
//                        int startB = startInt & 0xff;
//
//                        int endInt = (Integer) endValue;
//                        int endA = (endInt >> 24);
//                        int endR = (endInt >> 16) & 0xff;
//                        int endG = (endInt >> 8) & 0xff;
//                        int endB = endInt & 0xff;
//
//                        float delta = (float) (15 * fraction);
//
//                        Log.d(TAG, "fraction" + fraction);
//                        spannableString.setSpan(new AbsoluteSizeSpan(sp2px(15 + delta)), length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
//                                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
//                                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
//                                (int)((startB + (int)(fraction * (endB - startB))));

                        float delta = (float) (15 * fraction);
                        spannableString.setSpan(new AbsoluteSizeSpan(sp2px(15 + delta)), length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        return new ArgbEvaluator().evaluate(fraction, startValue, endValue);
                    }
                });
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //refresh
                        mTvTextView.setText(spannableString);
                    }
                });
                objectAnimator.setDuration(2000);
                objectAnimator.start();


//                // ...
//                ObjectAnimator objectAnimator1 = ObjectAnimator.ofInt(span, , Color.WHITE, Color.BLACK);
//
//                objectAnimator1.setEvaluator(new ArgbEvaluator());
//                objectAnimator1.setDuration(600);
//                objectAnimator1.start();

            }
        });
    }

    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private static final Property<MutableForegroundColorSpan, Integer> MUTABLE_FOREGROUND_COLOR_SPAN_PROPERTY =
        new Property<MutableForegroundColorSpan, Integer>(Integer.class, "MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY") {

            @Override
            public void set(MutableForegroundColorSpan alphaForegroundColorSpanGroup, Integer value) {
                Log.d(TAG, "value" + value);
                alphaForegroundColorSpanGroup.setForegroundColor(value);
            }

            @Override
            public Integer get(MutableForegroundColorSpan span) {
                return span.getForegroundColor();
            }
        };
}