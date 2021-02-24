package com.example.practise.textviewanimation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Property;
import android.util.TypedValue;
import android.widget.TextView;

import com.example.practise.spanable.MutableForegroundColorSpan;

/**
 * 字体动画大小从50%变成100%
 * 字体需要根据TextView的改变而实时改变。
 */
public class AnimationHelper {

    private static final int ANIMATOR_DURATION = 250;
    private Resources mResources;
    private EditTextPlus mTextView;
    private float mTextSize;

    public AnimationHelper(Resources r, EditTextPlus tv) {
        mResources = r;
        mTextView = tv;
    }

    public void setFontSize(float textSize) {
        mTextSize = textSize;
    }

    public void setContent(String newStr, int position) {
        MutableForegroundColorSpan span = new MutableForegroundColorSpan(255, Color.BLUE);

        SpannableString spannableString = new SpannableString(newStr);
        spannableString.setSpan(span, position, position + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        float halfTextSize = mTextSize / 2;

        spannableString.setSpan(new AbsoluteSizeSpan((int) halfTextSize), position, position + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(span, MUTABLE_FOREGROUND_COLOR_SPAN_PROPERTY, Color.WHITE, Color.BLACK);

        objectAnimator.setEvaluator((fraction, startValue, endValue) -> {
            float workInProcessFontSize = halfTextSize * (1 + fraction);
            spannableString.setSpan(new AbsoluteSizeSpan((int) workInProcessFontSize), position, position + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return new ArgbEvaluator().evaluate(fraction, startValue, endValue);
        });

        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTextView.setTextContent(spannableString, position);
            }
        });
        objectAnimator.setDuration(ANIMATOR_DURATION);
        objectAnimator.start();
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mResources.getDisplayMetrics());
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
