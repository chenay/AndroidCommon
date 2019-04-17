package com.chenay.common.desgin.spinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.chenay.common.R;
import com.chenay.common.desgin.popup.ChyBasePopupWindow;
import com.chenay.common.utils.Utils_UI;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 下啦
 *
 * @author Y.Chen5
 * @date 3/29/2018
 */

public class ChySpinner extends LinearLayout implements View.OnClickListener, PopupWindow.OnDismissListener {
    private static final String TAG = "ChySpinner";

    private TextView mchy_spinner_hint;
    private LinearLayout mchy_spinner_show;
    private TextView mchy_spinner_content;
    private ImageView mchy_spinner_img;
    private float expandHeight = 0;
    private List<String> list;
    private SpinnerPopup spinnerPopup;
    public BaseAdapter adapter;

    public ChySpinner(Context context) {
        this(context, null);
    }

    public ChySpinner(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChySpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.chy_spinner_layout, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChySpinner);
        initView(typedArray);
    }

    private void initView(TypedArray typedArray) {
        float hintSize = typedArray.getDimension(R.styleable.ChySpinner_hintTextSize, getResources().getDimension(R.dimen.sp_17));
        CharSequence hintText = typedArray.getText(R.styleable.ChySpinner_hintText);
        expandHeight = typedArray.getDimension(R.styleable.ChySpinner_popupHeight, getResources().getDimension(R.dimen.dp_200));
        int hintColor = typedArray.getColor(R.styleable.ChySpinner_hintTextColor, Color.BLACK);
        int textColor = typedArray.getColor(R.styleable.ChySpinner_textColor, Color.BLACK);
        typedArray.recycle();
        hintSize = Utils_UI.px2sp(getContext(), hintSize);
        expandHeight = Utils_UI.px2dp(getContext(), expandHeight);

        mchy_spinner_hint = (TextView) findViewById(R.id.chy_spinner_hint);
        mchy_spinner_show = (LinearLayout) findViewById(R.id.chy_spinner_show);
        mchy_spinner_content = (TextView) findViewById(R.id.chy_spinner_content);
        mchy_spinner_img = (ImageView) findViewById(R.id.chy_spinner_img);

        mchy_spinner_content.setTextColor(textColor);
        mchy_spinner_hint.setText(hintText);
        mchy_spinner_hint.setTextSize(hintSize);
        mchy_spinner_hint.setTextColor(hintColor);
        mchy_spinner_show.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mchy_spinner_show) {
            list = new ArrayList<>();
            spinnerPopup = new SpinnerPopup((v.getContext()));
            spinnerPopup.setOnDismissListener(this);
            spinnerPopup.setWidth_H(v.getMeasuredWidth(), (int) expandHeight);
            mchy_spinner_img.setImageResource(R.drawable.ic_expand_less_black);

            spinnerPopup.showAsDropDown(v);
            if (mChySpinnerCallBack != null) {
                mChySpinnerCallBack.choosing(this);
            }
        }
    }

    /**
     * 显示下拉
     */
    public void showSpinner() {
        spinnerPopup = new SpinnerPopup(mchy_spinner_show.getContext());
        spinnerPopup.setOnDismissListener(this);
        spinnerPopup.setWidth_H(mchy_spinner_show.getMeasuredWidth(), (int) expandHeight);
        mchy_spinner_img.setImageResource(R.drawable.ic_expand_less_black);
        spinnerPopup.showAsDropDown(mchy_spinner_show);
        if (mChySpinnerCallBack != null) {
            mChySpinnerCallBack.choosing(this);
        }
    }

    /**
     * 待扩展
     */
    private void setAdapter() {

    }

    @Deprecated
    public void setArrayAdapter(List<String> itemList) {
//        if (itemList == null) {
//            this.list = new ArrayList<>();
//        } else {
//            this.list = itemList;
//        }
//        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
//        adapter.notifyDataSetChanged();
    }


    public void notifyDataSetChanged(List<String> itemList) {
        if (list != null) {
            list.clear();
            list.addAll(itemList);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        } else {
            this.list = itemList;
            if (adapter == null) {
                Log.e(TAG, "notifyDataSetChanged:  NullPointerException(adapter is null , 请在回调choosing() 后调用notifyDataSetChanged");
                return;
            }
            adapter.notifyDataSetChanged();
        }
    }


    public CharSequence getText() {
        return mchy_spinner_content.getText();
    }

    public void setText(CharSequence text) {
        mchy_spinner_content.setText(text);
    }

    public void setError(String errorText) {
        mchy_spinner_content.setError(errorText);
    }

    @Override
    public void onDismiss() {
        mchy_spinner_img.setImageResource(R.drawable.ic_expand_more_black);
        if (list != null) {
            list.clear();
            list = null;
        }
    }

    class SpinnerPopup extends ChyBasePopupWindow {
        SpinnerPopup(Context context) {
            super(context, R.layout.chy_spinner_popuplayout, true);
            setAnimationStyle(R.style.mypopwindow_top_scale);
        }

        @Override
        protected void initView() {
            ListView mchy_spinner_list = (ListView) getContentView().findViewById(R.id.chy_spinner_list);
            if (list == null) {
                list = new ArrayList<>();
            }
            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
            mchy_spinner_list.setAdapter(adapter);
            mchy_spinner_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mchy_spinner_content.setError(null);
                    mchy_spinner_content.setText(list.get(position));
                    if (mChySpinnerCallBack != null) {
                        mChySpinnerCallBack.chose(ChySpinner.this, list.get(position));
                    }
                    SpinnerPopup.this.dismiss();
                }
            });
        }
    }

    /**
     * 内部监听
     * author:chenay
     */
    private ChySpinnerCallBack mChySpinnerCallBack;

    /**
     * 内部监听接口
     * author:chenay
     */
    public interface ChySpinnerCallBack {

        /**
         * 跳出选择窗口是调用
         * 正在选择
         *
         * @param chySpinner
         */
        void choosing(ChySpinner chySpinner);

        /**
         * 选择完成
         * 关闭选择窗口是调用
         *
         * @param chySpinner
         * @param s
         */
        void chose(ChySpinner chySpinner, String s);
    }

    /**
     * 注册内部监听
     * author:chenay
     */
    public void registerChySpinnerCallBack(ChySpinnerCallBack mChySpinnerCallBack) {
        this.mChySpinnerCallBack = mChySpinnerCallBack;
    }


}

