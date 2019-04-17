package com.chenay.common.utils;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewAnimationUtils;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenay.common.R;

import static android.widget.Toast.makeText;


/**
 * Created by Chenay on 2016/7/4.
 */
public class Utils_UI {
    private static final String TAG = "Utils_UI";

    /**
     * 获取尺寸
     *
     * @param context
     * @param id
     * @return
     */
    public static int getDimens(Context context, int id) {
        return (int) context.getResources().getDimension(id);
    }

    /**
     * 获取屏幕的一些属性
     *
     * @param context
     */
    public static void getDefultDisPlay_atti(Context context) {
        int screenWidth;
        int screenHeight;
        WindowManager windowManager = ((Activity) context).getWindowManager();
        DisplayMetrics densityDpi = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(densityDpi);
        screenWidth = densityDpi.widthPixels;
        screenHeight = densityDpi.heightPixels;
        int densityDPI = densityDpi.densityDpi;     // 屏幕密度（每寸像素：120(ldpi)/160(mdpi)/213(tvdpi)/240(hdpi)/320(xhdpi)）
        makeText(context, "真实分辨率：" + screenWidth + "*" + screenHeight + "  屏幕密度:" + densityDPI, Toast.LENGTH_LONG).show();
        Log.d(TAG, "getDefultDisPlay_atti: " + "真实分辨率：" + screenWidth + "*" + screenHeight + "  屏幕密度:");
    }


    /**
     * 返回语言
     *
     * @param context
     * @param id
     * @return
     */
    public static String getLanStr(Context context, int id) {
        return context.getResources().getString(id);
    }

    /**
     * 获取list的高度
     *
     * @param listView
     * @return
     */
    public static int getTotalHeightofListView(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        return totalHeight;
    }

    /**
     * 获取list的宽度
     *
     * @param listView
     * @return
     */
    public static int getTotalWidthofListView(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return 0;
        }
        int totalWidth = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            totalWidth = mView.getMeasuredWidth() > totalWidth ? mView.getMeasuredWidth() : totalWidth;
        }
        return totalWidth;
    }


    /**
     * 强制隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void setHideSoftInput(Context context, View view) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 将控件裁剪成圆形
     *
     * @param view
     */
    public static void setPorvider(View view) {

        ViewOutlineProvider provider = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            provider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int width = view.getWidth();
                    int height = view.getHeight();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outline.setRoundRect(0, 0, width, width, width / 2);
                    }
                }
            };
            view.setClipToOutline(true);
            view.setOutlineProvider(provider);
        }

    }

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenW(Context context) {
        WindowManager manager = ((Activity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width2 = outMetrics.widthPixels;
        return width2;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenH(Context context) {
        WindowManager manager = ((Activity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int h = outMetrics.heightPixels;
        return h;
    }


    /**
     * 自定义toast
     *
     * @param context
     * @param str
     */
    public static void createToast(Context context, String str) {
        if (true) {
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void createToast(Context context, int id) {
        String str = Utils_UI.getLanStr(context, id);
        makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 错误提示窗口
     *
     * @param str 为提示的内容
     */
    public static void WarningDialog(Context context, String str) {

        TextView textView = new TextView(context);
        textView.setText(str);
        textView.setTextColor(Color.RED);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 27);
        Toast toast = new Toast(context);
        toast.setView(textView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, (int) context.getResources().getDimension(R.dimen.dp_200));
        toast.show();
        toast = null;
        // return toast;
    }

    public static void mYhandleToast(Handler handler, final Context context, final String string) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                createToast(context, string);
            }
        });
    }


    // 获取指定Activity的截屏，保存到png文件
    public static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
//        Logger.i("TAG", "" + statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }


    /**
     * 获取View的截图
     *
     * @param view
     * @return
     */
    public static Bitmap takeViewShot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap tBitmap = view.getDrawingCache();

        return tBitmap;
    }


    public interface MyViewListenter {
        void getW_H(int width, int height);
    }

    /**
     * 获取控件的宽高
     *
     * @param listenter
     * @param view
     */
    public static void getViewW_H(final MyViewListenter listenter, final View view) {
        ViewTreeObserver vto2 = view.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (listenter != null) {
                    listenter.getW_H(view.getWidth(), view.getHeight());
                }
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }


    /**
     * 将view裁剪成圆形
     *
     * @param view
     */
    public static void setRoundRect(final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewTreeObserver vto2 = view.getViewTreeObserver();
            vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    setPorvider(view);
                }
            });
        }
    }

    /**
     * create CircularReveal animation
     */
    public static Animator createAnimation(View v) {
        // create a CircularReveal animation
        /**
         *开始点 x, y, 开始半径,结束半径
         */
        Animator animator = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils.createCircularReveal(
                    v,
                    v.getWidth() / 2,
                    v.getHeight() / 2,
                    0,
                    v.getWidth());
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(500);
        }
        if (animator != null) {
            animator.start();
        }
        return animator;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param
     * @return
     */

    public static float px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static float px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }


}
