package com.example.practise.edittext;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import com.example.practise.R;

/*
 * This is supposed to be a *very* thin veneer over TextView.
 * Do not make any changes here that do anything that a TextView
 * with a key listener and a movement method wouldn't do!
 */

/**
 * A user interface element for entering and modifying text.
 * When you define an edit text widget, you must specify the
 * {@link android.R.styleable#TextView_inputType}
 * attribute. For example, for plain text input set inputType to "text":
 * <p>
 * <pre>
 * &lt;EditText
 *     android:id="@+id/plain_text_input"
 *     android:layout_height="wrap_content"
 *     android:layout_width="match_parent"
 *     android:inputType="text"/&gt;</pre>
 * <p>
 * Choosing the input type configures the keyboard type that is shown, acceptable characters,
 * and appearance of the edit text.
 * For example, if you want to accept a secret number, like a unique pin or serial number,
 * you can set inputType to "numericPassword".
 * An inputType of "numericPassword" results in an edit text that accepts numbers only,
 * shows a numeric keyboard when focused, and masks the text that is entered for privacy.
 * <p>
 * See the <a href="{@docRoot}guide/topics/ui/controls/text.html">Text Fields</a>
 * guide for examples of other
 * {@link android.R.styleable#TextView_inputType} settings.
 * </p>
 * <p>You also can receive callbacks as a user changes text by
 * adding a {@link android.text.TextWatcher} to the edit text.
 * This is useful when you want to add auto-save functionality as changes are made,
 * or validate the format of user input, for example.
 * You add a text watcher using the {@link TextView#addTextChangedListener} method.
 * </p>
 * <p>
 * This widget does not support auto-sizing text.
 * <p>
 * <b>XML attributes</b>
 * <p>
 * See {@link android.R.styleable#EditText EditText Attributes},
 * {@link android.R.styleable#TextView TextView Attributes},
 * {@link android.R.styleable#View View Attributes}
 */
public class EditTextTest extends TextView {
    private Context mContext;

    public EditTextTest(Context context) {
        this(context, null);
    }

    public EditTextTest(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public EditTextTest(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public EditTextTest(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    @Override
    public boolean getFreezesText() {
        return true;
    }

    @Override
    protected boolean getDefaultEditable() {
        return true;
    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return ArrowKeyMovementMethod.getInstance();
    }

    @Override
    public Editable getText() {
        CharSequence text = super.getText();
        // This can only happen during construction.

        if (text == null) {
            return null;
        }
        if (text instanceof Editable) {
            return (Editable) super.getText();
        }
        super.setText(text, BufferType.EDITABLE);
        return (Editable) super.getText();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, BufferType.EDITABLE);
    }

    /**
     * Convenience for {@link Selection#setSelection(Spannable, int, int)}.
     */
    public void setSelection(int start, int stop) {
        Selection.setSelection(getText(), start, stop);
    }

    /**
     * Convenience for {@link Selection#setSelection(Spannable, int)}.
     */
    public void setSelection(int index) {
        Selection.setSelection(getText(), index);
    }

    /**
     * Convenience for {@link Selection#selectAll}.
     */
    public void selectAll() {
        Selection.selectAll(getText());
    }

    /**
     * Convenience for {@link Selection#extendSelection}.
     */
    public void extendSelection(int index) {
        Selection.extendSelection(getText(), index);
    }

    /**
     * Causes words in the text that are longer than the view's width to be ellipsized instead of
     * broken in the middle. {@link TextUtils.TruncateAt#MARQUEE
     * TextUtils.TruncateAt#MARQUEE} is not supported.
     *
     * @param ellipsis Type of ellipsis to be applied.
     * @throws IllegalArgumentException When the value of <code>ellipsis</code> parameter is
     *                                  {@link TextUtils.TruncateAt#MARQUEE}.
     * @see TextView#setEllipsize(TextUtils.TruncateAt)
     */
    @Override
    public void setEllipsize(TextUtils.TruncateAt ellipsis) {
        if (ellipsis == TextUtils.TruncateAt.MARQUEE) {
            throw new IllegalArgumentException("EditText cannot use the ellipsize mode "
                    + "TextUtils.TruncateAt.MARQUEE");
        }
        super.setEllipsize(ellipsis);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return android.widget.EditText.class.getName();
    }

    /**
     * @hide
     */
//    @Override
//    protected boolean supportsAutoSizeText() {
//        return false;
//    }

    /**
     * @hide
     */
//    @Override
//    protected boolean supportsAutoSizeText() {
//        return true;
//    }

    /**
     * @hide
     */
//    @Override
//    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
//        super.onInitializeAccessibilityNodeInfoInternal(info);
//        if (isEnabled()) {
//            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT);
//        }
//    }

    public interface SelectionChangedListener {
        void onSelectionChanged(int selStart, int selEnd);
    }

    // 计算每一行的宽度，在需要的地方加 "\n" 。
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (listener != null) {
            listener.onSelectionChanged(selStart, selEnd);
        }
    }

    private EditTextCursorWatcher.SelectionChangedListener listener;

    public void setSelectionChangedListener(EditTextCursorWatcher.SelectionChangedListener listener) {
        this.listener = listener;
    }

    private CharSequence mText;

    public void setTextContent(CharSequence text) {
        // 如果是普通的数字，则不改变text


        Log.d("test1","getTextSize()" + getTextSize());
        Log.d("test1","dpTopx(getContext(), 23)" + dpTopx(getContext(), 23));

        if ((int) getTextSize() <= dpTopx(getContext(), 23)) {
            Log.d("test1", "true");
//            setMaxLines(getMaxLines() + 1);
            text = text + "\n";
        }

        mText = text;
        if (mText.toString().contains("+️")) {
            int index = mText.toString().indexOf("+");
            mText = mText.subSequence(0, index) + "\n" + mText.subSequence(index, mText.toString().length());
        }

        super.setText(mText);
    }

    public static int dpTopx(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    public static int pxTodp(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
}
