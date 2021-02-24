package com.example.practise.textviewanimation;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.practise.R;

/**
 * Todo
 * 1、支持回退 done
 * 2、支持编辑。双击会出现弹窗，需要禁止，需要重写touch事件
 * 3、动画，缺少反向动效。暂时先不做，done
 * 4、支持多符号。目前只支持 -+×÷
 * 5、使用方无需知道编辑模式，只需调用新增或者删除，内部实现编辑模式的处理 done
 */
public class EditTextPlus extends androidx.appcompat.widget.AppCompatEditText implements View.OnClickListener {

    private final static int CHANGE_STEP = 3;
    private CharSequence mText;
    private Paint mTempPaint;
    private int mMinTextSize;
    private float mTextSize;
    private boolean mIsMin;
    private boolean mIsMax;
    private float mMaxTextSize;
    private boolean mIsSingleLine = true;
    private boolean mNotRender;
    private AnimationHelper mAnimationHelper;
    private int mSelectionPosition;
    private boolean mIsInEditMode;

    public EditTextPlus(Context context) {
        this(context, null);
    }

    public EditTextPlus(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public EditTextPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTempPaint = new Paint();
        mMaxTextSize = getTextSize();
        mMinTextSize = getResources().getDimensionPixelSize(R.dimen.cal_minTextSize); // 也是px单位
        mAnimationHelper = new AnimationHelper(getResources(), this);

        setShowSoftInputOnFocus(false);
        setOnClickListener(this);
        setOnFocusChangeListener((v, h) -> setCursorVisibleStatus(true));
        setOnLongClickListener(v -> true);
    }

    @Override
    public void onClick(View v) {
        setCursorVisibleStatus(true);
    }

    public void blur() {
        setCursorVisibleStatus(false);
    }

    public void setMinTextSize(int s) {
        mMinTextSize = s;
    }

    public void appendContent(String appendString) {
        if (mIsInEditMode) {
            appendInEditMode(appendString);
        } else {
            setContent(getText() + appendString, getText().toString().length());
        }
    }

    public void deleteContent() {
        if (mIsInEditMode) {
            deleteInEditMode();
        } else {
            String text = getText().toString();
            text = text.substring(0, text.length() - 1);
            setContent(text);
        }
    }

    public void setTextContent(CharSequence text) {
        super.setText(text);
    }

    // spannable动画结束后才能设置光标位置。
    public void setTextContent(CharSequence text, int position) {
        super.setText(text);
        if (mIsInEditMode) {
            setSelection(position + 1);
        }
    }

    public void setContent(String text, int position) {
        setAndAdjustFontSize(text, false);

        if (mNotRender) {
            mNotRender = false;
            return;
        }

        // 执行spannable动画，需要重新获取计算后的字体大小
        mAnimationHelper.setFontSize(mTextSize);
        mAnimationHelper.setContent(mText.toString(), position);
    }

    public void setContent(String text) {
        setAndAdjustFontSize(text, true);
        setTextContent(mText);
    }

    // 光标的处理
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        mSelectionPosition = selEnd;
    }

    private void setAndAdjustFontSize(String text, boolean isBack) {
        mText = text;
        mTextSize = getTextSize();
        if (isBack) {
            increaseFontSize(mTextSize);
            reLayoutForBack();
        } else {
            reduceFontSize(mTextSize);
            reLayoutText();
        }
    }

    // 编辑模式下，新增的数字光标不变
    private void appendInEditMode(String s) {
        String str = getText().toString();
        int pos = mSelectionPosition;
        str = Utils.appendCharAt(str, s, pos);
        setContent(str, pos);
    }

    // 移除字符串时，需要将Spannable一起移除
    public void deleteInEditMode() {
        if (mSelectionPosition < 1) return;

        String str = getText().toString();
        int pos = mSelectionPosition - 1;
        str = Utils.removeCharAt(str, pos);

        Spannable span = getSpannable();
        ForegroundColorSpan[] old = span.getSpans(0, getSpannable().length(), ForegroundColorSpan.class);
        for (int i = old.length - 1; i >= 0; i--) {
            span.removeSpan(old[i]);
        }
        setContent(str);
        setSelection(pos);
    }

    private void setCursorVisibleStatus(boolean bol) {
        mIsInEditMode = bol;
        setCursorVisible(bol);
    }

    // 通过 paint 计算真实宽度
    private float getStringWidth(float size, String str) {
        mTempPaint.setTextSize(size);
        float strWidth = mTempPaint.measureText(str);
        return strWidth;
    }

    private Spannable getSpannable() {
        return getText();
    }

    private void increaseFontSize(float textSize) {
        if (mIsMax) return;

        float newTextSize = textSize + CHANGE_STEP;

        if (getStringWidth(newTextSize, mText.toString()) > getMeasuredWidth()) {
            mIsMin = false;
            if (textSize == getTextSize()) return;
            setAndSaveTextSize(textSize);
        } else {
            if (newTextSize > mMaxTextSize) {
                mIsMax = true;
                setAndSaveTextSize(mMaxTextSize);
                return;
            } else {
                mIsMax = false;
            }
            increaseFontSize(newTextSize);
        }
    }

    private void reduceFontSize(float textSize) {
        if (mIsMin) return;

        if (getStringWidth(textSize, mText.toString()) > getMeasuredWidth()) {
            textSize -= CHANGE_STEP;

            // 如果已经达到最小字体，则直接设置最小字体。
            if (textSize < mMinTextSize) {
                mIsMin = true;
                setAndSaveTextSize(mMinTextSize);
                return;
            } else {
                mIsMin = false;
            }

            reduceFontSize(textSize);
        } else {
            mIsMax = false;
            if (textSize == getTextSize()) return;
            setAndSaveTextSize(textSize);
        }
    }

    private void setAndSaveTextSize(float textSize) {
        mTextSize = textSize;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    private String[] getTextList() {
        if (mText == null || mText == "") return new String[0];
        String[] list = mText.toString().split("\n");
        return list;
    }

    private String getCurrentLineText() {
        String[] list = getTextList().clone();

        if (list.length > 1) {
            mIsSingleLine = false;
        } else {
            mIsSingleLine = true;
        }

        return (String) list[list.length - 1];
    }

    /**
     * 如果不是最小字体，不用处理换行逻辑
     * 如果当前行全是数字，不换行。多行情况下，如果最后一行只有一个符号，不换行。
     */
    private void reLayoutText() {
        if (!mIsMin) return;

        String currentLineText = getCurrentLineText();

        int index = SymbolRule.indexOf(currentLineText);
        if (index == -1) {
            mNotRender = true;
            return;
        }

        if (getStringWidth(mTextSize, currentLineText) < getMeasuredWidth()) return;

        if (mIsSingleLine) {
            if (index > -1) {
                mText = mText.subSequence(0, index) + "\n" + mText.subSequence(index, mText.toString().length());
            }
        } else {
            // 两行及以上逻辑
            int sIndex = SymbolRule.findSecondSymbol(currentLineText);
            if (sIndex > -1) {
                String[] list = getTextList().clone();
                String text = currentLineText.subSequence(0, sIndex) + "\n" + currentLineText.subSequence(sIndex, currentLineText.length());
                list[list.length - 1] = text;

                String str = String.join("\n", list);
                mText = str;
            } else {
                mNotRender = true;
            }
        }
    }

    /**
     * 删除的情况
     * 判断当前行和上一行组成的内容是否超过一行，超过一行，只删除当前行内容，否则两行合并。
     */
    private void reLayoutForBack() {
        String[] list = getTextList().clone();
        if (list.length <= 1) return;

        String currentLineText = getCurrentLineText();
        String preLineText = list[list.length - 2];
        String newLineText = preLineText + currentLineText;

        if (getStringWidth(mTextSize, newLineText) > getMeasuredWidth()) return;

        list[list.length - 1] = "";
        list[list.length - 2] = newLineText;

        String str = String.join("\n", list);
        mText = str;
    }
}
