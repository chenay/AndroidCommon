package com.chenay.common.utils;

import android.content.res.Resources;

public class DensityUtil {
    private static float density = Resources.getSystem().getDisplayMetrics().density;

    public static int dip2px(int dp){
        return (int) (dp*density);
    }
    public static int dip2px(float dp){
        return (int) (dp*density);
    }
    public static int appWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
