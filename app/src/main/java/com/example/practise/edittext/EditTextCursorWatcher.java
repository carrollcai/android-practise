package com.example.practise.edittext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;

public class EditTextCursorWatcher extends androidx.appcompat.widget.AppCompatEditText {
    private SelectionChangedListener listener;
    private CharSequence mText;
    private Context mContext;
    private TextPaint mTempTextPaint;
    private TextPaint mTextPaint;
    private int mMinTextSize;
    private boolean mIsEnableMinTextSize;

    public interface SelectionChangedListener {
        void onSelectionChanged(int selStart, int selEnd);
    }

    public EditTextCursorWatcher(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mTempTextPaint = new TextPaint();
//        mTextHelper = new TextViewHelper(context, this);
//        mTextHelper.autoSizeText();
    }

    public EditTextCursorWatcher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextCursorWatcher(Context context) {
        this(context, null, 0);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
//        if (mTextHelper != null) {
//            mTextHelper.autoSizeText();
//        }
    }

    public void setSelectionChangedListener(SelectionChangedListener listener) {
        this.listener = listener;
    }

    // 计算每一行的宽度，在需要的地方加 "\n" 。
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (listener != null) {
            listener.onSelectionChanged(selStart, selEnd);
        }
    }

    public void setTextContent(CharSequence text) {
        // 如果是普通的数字，则不改变text

        mText = text;
        if (mText.toString().contains("+️")) {
            int index = mText.toString().indexOf("+");
            mText = mText.subSequence(0, index) + "\n" + mText.subSequence(index, mText.toString().length());
        }

        super.setText(mText);
    }

    public void setMinTextSizePx(int sizePx) {
        mMinTextSize = sizePx;
    }

    public void enableMinTextSize(boolean enable) {
        mIsEnableMinTextSize = enable;
    }

    // 1
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        int width, height;
////        mParentMeasureSpecWidth = widthSize;
//        //如果使能了最小字体,当前字体小于最小字体,则修正字体大小
//        if (mIsEnableMinTextSize && getTextSize() < mMinTextSize) {
//            getPaint().setTextSize(mMinTextSize);
//        }
//
//        if (widthMode == MeasureSpec.EXACTLY) {
//            // Parent has told us how big to be. So be it.
//            width = widthSize;
//        } else {
//            int desiredWidth = getDesiredWidth() + getPaddingLeft() + getPaddingRight();
//            width = Math.min(desiredWidth, widthSize);
//        }
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            // Parent has told us how big to be. So be it.
//            height = heightSize;
//        } else {
//            int desiredHeight = getDesiredHeight(width);
//            height = Math.min(desiredHeight, heightSize);
//        }
//
//        int suggestSize = 0;
//
        mTempTextPaint.reset();
        mTempTextPaint.set(getPaint());
//        mTempTextPaint.setTextSize(suggestSize);
//
//        setMeasuredDimension(width, height);

        // 计算长度是否超出，
        // 每次尺寸 - 1，直到没有超出。记录之前的行数，如果超过，行数会加1。行数 + 1，回退。
        // 最终生成 需要渲染的字符串。


//        if (mText.equals("")) {
//            return;
//        }
//        if (mText.toString().contains("+️")) {
//            int index = mText.toString().indexOf("+");
//            mText = mText.subSequence(0, index) + "\n" + mText.subSequence(index, mText.toString().length());
//        }
    }

    // 2
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        // 渲染方式自己写。 得到测量之后的值。

        if (mText == null) {
            super.onDraw(canvas);
        } else {
            createStaticLayout(mText);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        autoSizeText();
    }

    private int getDesiredHeight(int width) {
        return 0;
    }

    private int getDesiredWidth() {
        return 0;
    }

    private StaticLayout createStaticLayout(CharSequence text) {
        final float lineSpacingMultiplier = getLineSpacingMultiplier();
        final float lineSpacingAdd = getLineSpacingExtra();
        final boolean includePad = getIncludeFontPadding();

        // The layout could not be constructed using the builder so fall back to the
        // most broad constructor.
        return new StaticLayout(text, mTempTextPaint, getWidth(),
                Layout.Alignment.ALIGN_NORMAL,
                lineSpacingMultiplier,
                lineSpacingAdd,
                includePad);
    }

    void initTempTextPaint(final int suggestedSizeInPx) {
        if (mTempTextPaint == null) {
            mTempTextPaint = new TextPaint();
        } else {
            mTempTextPaint.reset();
        }
        mTempTextPaint.set(getPaint());
        mTempTextPaint.setTextSize(suggestedSizeInPx);
    }

    void autoSizeText() {
        initTempTextPaint(15);
    }
}
