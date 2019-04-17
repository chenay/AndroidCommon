package com.chenay.common.desgin.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chenay.common.R;


/**
 * 1
 *
 * @author Y.Chen5
 * @date 3/28/2018
 */

public abstract class ChyBasePopupWindow extends PopupWindow {

    public final Context context;

    /**
     * example super(context, R.layout.fast_search_layout, true);
     *
     * @param context     上下文环境
     * @param layoutId     关联的布局文件
     * @param isFocusable  是否需要焦点
     */
    public ChyBasePopupWindow(Context context, int layoutId, Boolean isFocusable) {
        this(context);
        mSetContentView(layoutId, isFocusable);
        initView();
    }

    private ChyBasePopupWindow(Context context) {
        super(context);
        this.context = context;
    }

    protected View mSetContentView(int viewLayout, Boolean isFocusable) {
        View mainView = LayoutInflater.from(context).inflate(viewLayout, null);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(mainView);
        setFocusable(isFocusable);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return mainView;
    }

    protected abstract void initView();

    /**
     * 设置自适应宽高
     */
    public void setWidth_H() {
        int height = getContentView().getMeasuredHeight();
        int width = getContentView().getMeasuredWidth();
        setWidth(width);
        setHeight(height);
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     */
    public void setWidth_H(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * 设置宽高
     *
     * @param wDimens
     * @param hDimens
     */
    public void setWidth_H_Id(int wDimens, int hDimens) {
        setWidth_H(getDimens(wDimens), getDimens(hDimens));
    }

    /**
     * 获取尺寸
     *
     * @param id
     * @return
     */
    private int getDimens(int id) {
        return (int) context.getResources().getDimension(id);
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
     * 已父控件的左上角为基准
     *
     * @param parent
     * @param v      ########颜色填充专用######
     */
    public void mShowAtLocation1(View parent, View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
//        int screenh = ToolsPubulic_UI.getScreenH(inflater.getContext());
//        int y2 = getContentView().getMeasuredHeight();
//        int y1 = screenh-(screenh-y+y2);

        int x2 = getContentView().getMeasuredWidth();
        int i = x - (x2 - v.getWidth()) / 2;
        showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, i, getDimens(R.dimen.dp_62));
    }

    /**
     * ancher 右方对齐显示
     *
     * @param ancher
     */
    public void mShowAsDropDown_right(final View ancher) {
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
        showAtLocation(parentView, Gravity.TOP | Gravity.LEFT, x, y);
    }

    public void mShowAtLocation_top_left1(View parentView, int x, int y) {
        showAtLocation(parentView, Gravity.TOP | Gravity.LEFT, x, y);
    }

    public void mShowAtLocation_right_center(View parentView, int x, int y) {
        showAtLocation(parentView, Gravity.CENTER_VERTICAL | Gravity.RIGHT, x, y);
    }

}
