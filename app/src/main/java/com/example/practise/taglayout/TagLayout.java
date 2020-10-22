package com.example.practise.taglayout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    private List<Rect> mChildRectList = new ArrayList<>();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int lineHeightUsed = 0;
        int lineWidthUsed = 0;
        int widthUsed = 0;
        int heightUsed = 0;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            // 测量子 View 尺寸。TagLayout 的子 view 是可以换行的，所以设置 widthUsed 参数为 0
            // 让子 View 的尺寸不会受到挤压。
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            if (widthMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.getMeasuredWidth() > widthSize) {
                // 需要换行了
                lineWidthUsed = 0;
                heightUsed += lineHeightUsed;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            Rect childBound;
            if (mChildRectList.size() >= i) {
                // 不存在则创建
                childBound = new Rect();
                mChildRectList.add(childBound);
            } else {
                childBound = mChildRectList.get(i);
            }
            // 存储 child 位置信息
            childBound.set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(),
                    heightUsed + child.getMeasuredHeight());
            // 更新位置信息
            lineWidthUsed += child.getMeasuredWidth();
            // 获取一行中最大的尺寸
            lineHeightUsed = Math.max(lineHeightUsed, child.getMeasuredHeight());
            widthUsed = Math.max(lineWidthUsed, widthUsed);
        }

        // 使用的宽度和高度就是 TagLayout 的宽高啦
        heightUsed += lineHeightUsed;
        setMeasuredDimension(widthUsed, heightUsed);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mChildRectList.size() == 0) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (mChildRectList.size() <= i) {
                return;
            }
            View child = getChildAt(i);
            // 通过保存好的位置，设置子 View
            Rect rect = mChildRectList.get(i);
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
