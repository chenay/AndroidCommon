package com.chenay.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

/**
 * Toast相关工具类
 */
public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

//    private static Toast sToast;
//    private static boolean isJumpWhenMore = true;

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     *                       <p>{@code true}: 弹出新吐司<br>{@code false}: 只修改文本内容</p>
     *                       <p>如果为{@code false}的话可用来做显示任意时长的吐司</p>
     */
//    public static void init(boolean isJumpWhenMore) {
//        ToastUtils.isJumpWhenMore = isJumpWhenMore;
//    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToastSafe(Context context, final CharSequence text) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortToastSafe(Context context, final @StringRes int resId) {


        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortToastSafe(Context context, final @StringRes int resId, final Object... args) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortToastSafe(Context context, final String format, final Object... args) {
        ((Activity) context).runOnUiThread(() -> showToast(context, format, Toast.LENGTH_SHORT, args));
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToastSafe(Context context, final CharSequence text) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongToastSafe(Context context, final @StringRes int resId) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongToastSafe(Context context, final @StringRes int resId, final Object... args) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(context, resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongToastSafe(Context context, final String format, final Object... args) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(context, format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortToast(Context context, @StringRes int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortToast(Context context, @StringRes int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortToast(Context context, String format, Object... args) {
        showToast(context, format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongToast(Context context, @StringRes int resId) {
        showToast(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongToast(Context context, @StringRes int resId, Object... args) {
        showToast(context, resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongToast(Context context, String format, Object... args) {
        showToast(context, format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void showToast(Context context, @StringRes int resId, int duration) {
        showToast(context, context.getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(Context context, @StringRes int resId, int duration, Object... args) {
        showToast(context, String.format(context.getResources().getString(resId), args), duration);
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(Context context, String format, int duration, Object... args) {
        showToast(context, String.format(format, args), duration);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(Context context, CharSequence text, int duration) {
//        if (isJumpWhenMore) {
//            cancelToast();
//        }
        Toast sToast = null;
        if (sToast == null) {
            sToast = Toast.makeText(context, text, duration);
            TextView tv = (TextView) sToast.getView().findViewById(android.R.id.message);
            tv.setTextSize(18);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    /**
     * 取消吐司显示
     */
//    public static void cancelToast() {
//        if (sToast != null) {
//            sToast.cancel();
//            sToast = null;
//        }
//    }
}