package com.chenay.common.desgin.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author Y.Chen5
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 设置是否能够滑动翻页
     *
     * @param canScroll true 可以滑动翻页 , false 不能翻页
     */
    public void setCanScroll(boolean canScroll) {
        isCanScroll = canScroll;
    }
}
