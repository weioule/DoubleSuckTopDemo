package com.example.doublesucktopdemo;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by weioule
 * on 2019/6/26.
 */
public class Utils {
    public static int dp2px(int dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 计算屏幕宽度 getScreenWidth
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        int width = metric.widthPixels;
        return width;
    }

    /**
     * 计算屏幕高度 getScreenWidth
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        int height = metric.heightPixels;

        return height;
    }


    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
