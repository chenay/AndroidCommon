package com.chenay.common.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chenay.common.R;

/**
 * @author Y.Chen5
 */
public class BasePopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {


    public BasePopupWindow(Context context, int viewId) {
        super(context);
        mSetContentView(context, viewId);
    }


    public BasePopupWindow(Context context, ViewDataBinding binding) {
        super(context);
        final View root = binding.getRoot();
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(root);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    }

    private void mSetContentView(Context context, int viewLayout) {
        View mainView = LayoutInflater.from(context).inflate(viewLayout, null);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(mainView);
//        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    }

    @Override
    public void onDismiss() {

    }

    public void setWidth_H() {
        int height = getContentView().getMeasuredHeight();
        int width = getContentView().getMeasuredWidth();
        setWidth(width);
        setHeight(height);
    }

    public void setWidth_H(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * ancher 上方居中显示
     *
     * @param ancher
     */
    public void mShowAsDropDown_horizontalCent(final View ancher) {
        setWidth_H();
        showAsDropDown(ancher, -getWidth() / 2 + (ancher.getWidth() / 2), getDimens(R.dimen.dp_2));
    }

    /**
     * 上方居中向左偏移1/5
     *
     * @param ancher
     */
    public void mShowAsDropDown(final View ancher) {
        setWidth_H();
//        showAsDropDown(ancher, -getWidth() / 5, 2);
        showAsDropDown(ancher, -getDimens(R.dimen.dp_58), getDimens(R.dimen.dp_2));
    }

    /**
     * 正下方
     *
     * @param ancher
     */
    public void mShowAsDropDown_below(final View ancher) {
        setWidth_H();
        showAsDropDown(ancher);
    }

    /**
     * 已父控件的左上角为基准
     *
     * @param parent
     * @param v
     */
//    public void mShowAtLocation(View parent, View v) {
//        int[] location = new int[2];
//        v.getLocationOnScreen(location);
//        int x = location[0];
//        int y = location[1];
//        int screenh = Utils_UI.getScreenH(inflater.getContext());
//        int y2 = getContentView().getMeasuredHeight();
//        int y1 = screenh - (screenh - y + y2);
//
//        int x2 = getContentView().getMeasuredWidth();
//        int i = x - (x2 - v.getWidth()) / 2;
//        showAtLocation(parent, Gravity.TOP | Gravity.LEFT, i, y1 - 5);
//    }

    /**
     * ancher 右方对齐显示
     *
     * @param ancher
     */
    public void mShowAsDropDown_right(final View ancher) {
        setWidth_H();
//        int[] location = new int[2];
//        ancher.getLocationOnScreen(location);
//        int x = location[0];
//        int y = location[1];

        showAsDropDown(ancher, ancher.getWidth(), -ancher.getHeight());
    }

    /**
     * 父控件居中显示
     *
     * @param parent
     */
    public void mShowAtLocation_Center(View parent) {
        setWidth_H();
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    /**
     * 父控件左上角显示
     *
     * @param parentView
     * @param x
     * @param y
     */
    public void mShowAtLocation_top_left(View parentView, int x, int y) {
        setWidth_H();
        showAtLocation(parentView, Gravity.TOP | Gravity.LEFT, x, y);
    }

    public void mShowAtLocation_top_left1(View parentView, int x, int y) {
        showAtLocation(parentView, Gravity.TOP | Gravity.LEFT, x, y);
    }

    public void mShowAtLocation_right_center(View parentView, int x, int y) {
        setWidth_H();
        showAtLocation(parentView, Gravity.CENTER_VERTICAL | Gravity.RIGHT, x, y);
    }

    /**
     * 获取尺寸
     *
     * @param id
     * @return
     */
    public int getDimens(int id) {
        return (int) getContentView().getContext().getResources().getDimension(id);
    }


}
