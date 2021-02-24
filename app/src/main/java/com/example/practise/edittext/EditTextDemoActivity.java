package com.example.practise.edittext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.TintableBackgroundView;
import androidx.core.widget.AutoSizeableTextView;
import androidx.core.widget.TintableCompoundDrawablesView;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.practise.R;
import com.example.practise.spanable.MutableForegroundColorSpan;

/**
 * Todo
 * 1、TextView实现自定义换行，遇到+号换行
 * 2、字满了之后，自动缩放字体，直到达到最小字体。
 * 3、setOnFocusChangeListener 和 setOnClickListener 显示光标不同。 setCursorVisible为false，会导致 setOnFocusChangeListener 失效。看能不能找新的API。
 * 4、关于数字删除问题。
 *
 */
public class EditTextDemoActivity extends AppCompatActivity {
    private EditTextTest mTextView;
    private boolean cursorVisible;
    private int selectionPosition;
    private TextView invisibleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_demo);
        mTextView = findViewById(R.id.tv);
        mTextView.setShowSoftInputOnFocus(false);

        mTextView.setSelectionChangedListener((selStart, selEnd) -> {
            selectionPosition = selEnd;
        });

        findViewById(R.id.add_number).setOnClickListener(v -> {

            String random = String.valueOf(((int)(Math.random() * 100) % 10));
            MutableForegroundColorSpan span = new MutableForegroundColorSpan(255, Color.BLUE);

            int length = mTextView.getText().length();

            String newStr = mTextView.getText() + random;
            SpannableString spannableString = new SpannableString(newStr);
            spannableString.setSpan(span, length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableString.setSpan(new AbsoluteSizeSpan(sp2px(15)), length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_FOREGROUND_COLOR_SPAN_PROPERTY, Color.WHITE, Color.BLACK);
            objectAnimator.setEvaluator((fraction, startValue, endValue) -> {
                float delta = (15 * fraction);
                spannableString.setSpan(new AbsoluteSizeSpan(sp2px(15 + delta)), length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return new ArgbEvaluator().evaluate(fraction, startValue, endValue);
            });
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //refresh
                    mTextView.setTextContent(spannableString);
                }
            });
            objectAnimator.setDuration(250);
            objectAnimator.start();

        });

        findViewById(R.id.add_symbol).setOnClickListener(v -> {

//            String str = "\n+️";
            String str = "+️";
            MutableForegroundColorSpan span = new MutableForegroundColorSpan(255, Color.BLUE);

            int length = mTextView.getText().length();

            String newStr = mTextView.getText() + str;
            SpannableString spannableString = new SpannableString(newStr);
            spannableString.setSpan(span, length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableString.setSpan(new AbsoluteSizeSpan(sp2px(15)), length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_FOREGROUND_COLOR_SPAN_PROPERTY, Color.WHITE, Color.RED);
            objectAnimator.setEvaluator((fraction, startValue, endValue) -> {
                float delta = (15 * fraction);
                spannableString.setSpan(new AbsoluteSizeSpan(sp2px(15 + delta)), length, length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return new ArgbEvaluator().evaluate(fraction, startValue, endValue);
            });
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //refresh
                    mTextView.setTextContent(spannableString);
                }
            });
            objectAnimator.setDuration(250);
            objectAnimator.start();

        });

        findViewById(R.id.clear).setOnClickListener(v -> {
            mTextView.setTextContent("");
        });

        mTextView.setOnFocusChangeListener((v, h) -> {
            Log.d("mTextView", "setOnFocusChangeListener");
            cursorVisible = true;
            mTextView.setCursorVisible(cursorVisible);
        });

        mTextView.setOnClickListener((v) -> {
            Log.d("mTextView", "setOnClickListener");
            cursorVisible = true;
            mTextView.setCursorVisible(cursorVisible);
        });

        findViewById(R.id.cancel_focus).setOnClickListener(v -> {
            cursorVisible = false;
            mTextView.setCursorVisible(cursorVisible);
        });

        findViewById(R.id.clear_one).setOnClickListener(v -> {
            String str = mTextView.getText().toString();
            int pos = selectionPosition - 1;
            str = removeCharAt(str, pos);

            Spannable span = getSpannable();
            ForegroundColorSpan[] old = span.getSpans(0, getSpannable().length(), ForegroundColorSpan.class);
            for (int i = old.length - 1; i >= 0; i--) {
                span.removeSpan(old[i]);
            }
            mTextView.setTextContent(str);
            mTextView.setSelection(pos);
        });
    }

    private float autoSizeText(float size)  {
        return size / (getResources().getDisplayMetrics().density + 0.2f /*0.2f*/);
    }

    public Spannable getSpannable() {
        return mTextView.getText();
    }

    public static String removeCharAt(String s, int pos) {
        if (pos < 0) return s;
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private static final Property<MutableForegroundColorSpan, Integer> MUTABLE_FOREGROUND_COLOR_SPAN_PROPERTY =
            new Property<MutableForegroundColorSpan, Integer>(Integer.class, "MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY") {

                @Override
                public void set(MutableForegroundColorSpan alphaForegroundColorSpanGroup, Integer value) {
                    alphaForegroundColorSpanGroup.setForegroundColor(value);
                }

                @Override
                public Integer get(MutableForegroundColorSpan span) {
                    return span.getForegroundColor();
                }
            };
}