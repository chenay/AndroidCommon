package com.chenay.common.desgin.layout.refresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenay.common.R;
import com.chenay.common.utils.DensityUtil;

/**
 * @author Y.Chen5
 */
public class CyClassicalHeader extends FrameLayout implements CyRefreshHeader {
    private static final String TAG = "CyClassicalHeader";

    private ImageView arrawImg;
    private TextView textTitle;

    private RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    public CyClassicalHeader(@NonNull Context context) {
        this(context, null);
    }

    public CyClassicalHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CyClassicalHeader(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.HORIZONTAL);
        root.setGravity(Gravity.CENTER_VERTICAL);
        addView(root, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ((LayoutParams) root.getLayoutParams()).gravity = Gravity.CENTER;

        arrawImg = new ImageView(context);
        arrawImg.setImageResource(R.drawable.ic_arrow_down);
        arrawImg.setScaleType(ImageView.ScaleType.CENTER);
        root.addView(arrawImg);

        textTitle = new TextView(context);
        textTitle.setTextSize(13);
        textTitle.setText("下拉刷新...");
        textTitle.setTextColor(Color.parseColor("#999999"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = 20;
        root.addView(textTitle, params);

        rotateAnimation.setDuration(800);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        setPadding(0, DensityUtil.dip2px(15), 0, DensityUtil.dip2px(15));
    }

    @Override
    public long succeedRetention() {
        return 200;
    }

    @Override
    public long failingRetention() {
        return 0;
    }

    @Override
    public int refreshHeight() {
        return getHeight();
    }

    @Override
    public int maxOffsetHeight() {
        return 4 * getHeight();
    }


    boolean isReset = true;

    @Override
    public void onReset(CySwipeRefreshLayout refreshLayout) {
        Log.e(TAG, "----------------> onReset");
        arrawImg.setImageResource(R.drawable.ic_arrow_down);
        textTitle.setText("下拉刷新...");
        isReset = true;
        arrawImg.setVisibility(VISIBLE);
    }

    @Override
    public void onPrepare(CySwipeRefreshLayout refreshLayout) {
        Log.e(TAG, "----------------> onPrepare");
        arrawImg.setImageResource(R.drawable.ic_arrow_down);
        textTitle.setText("下拉刷新...");
    }

    @Override
    public void onRefresh(CySwipeRefreshLayout refreshLayout) {
        Log.e(TAG, "----------------> onRefresh");
        arrawImg.setImageResource(R.drawable.ic_loading);
        arrawImg.startAnimation(rotateAnimation);
        textTitle.setText("加载中...");
        isReset = false;
    }

    @Override
    public void onComplete(CySwipeRefreshLayout refreshLayout, boolean isSuccess) {
        Log.e(TAG, "----------------> onComplete");
        arrawImg.clearAnimation();
        arrawImg.setVisibility(GONE);
        if (isSuccess) {
            textTitle.setText("刷新完成...");
        } else {
            textTitle.setText("刷新失败...");
        }
    }

    boolean attain = false;

    @Override
    public void onScroll(CySwipeRefreshLayout refreshLayout, int distance, float percent, boolean refreshing) {
        //Log.e(TAG, "----------------> onScroll  " + percent);

        if (!refreshing && isReset) {
            if (percent >= 1 && !attain) {
                attain = true;
                textTitle.setText("释放刷新...");
                arrawImg.animate().rotation(-180).start();
            } else if (percent < 1 && attain) {
                attain = false;
                arrawImg.animate().rotation(0).start();
                textTitle.setText("下拉刷新...");
            }
        }
    }

}
