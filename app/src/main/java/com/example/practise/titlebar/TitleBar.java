package com.example.practise.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.practise.R;

public class TitleBar extends RelativeLayout {
    private int bg;
    private int titleColor;
    private ImageView back;
    private TextView content;
    private ImageView right;
    private String title;

    public TitleBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        titleColor = typedArray.getColor(R.styleable.TitleBar_title_color, Color.BLACK);
        title = typedArray.getString(R.styleable.TitleBar_title);
        bg = typedArray.getColor(R.styleable.TitleBar_bg, Color.WHITE);
        typedArray.recycle();
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.title_bar, this);
        root.setBackgroundColor(bg);

        back = findViewById(R.id.tb_image_view);
        content = findViewById(R.id.tb_text_view);
        right = findViewById(R.id.tb_image_view_right);

        content.setText(title);
        content.setTextColor(titleColor);
    }

    public void setLeftListener(OnClickListener onClickListener) {
        back.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener) {
        right.setOnClickListener(onClickListener);
    }
}
