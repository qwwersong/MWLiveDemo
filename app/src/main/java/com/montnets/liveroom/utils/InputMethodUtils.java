package com.montnets.liveroom.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * @author hubj
 * @version v1.0
 * @Description:
 * @date 2015-09-28 15:05
 */
public class InputMethodUtils {

    public static void hide(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()
                && (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null)) {
            try {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                //ignore
            }
        }
    }

    /**
     * 关闭软件盘
     *
     * @param context
     * @param v
     */
    public static void hideSoftwareKeyboard(Context context, View v) {
        if (context == null || v == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * toogle切换键盘显示，其它方式无效
     *
     * @param context
     */
    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软件盘
     *
     * @param context
     * @param v
     */
    public static void showSoftwareKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }

    /**
     * 关闭软键盘
     *
     * @param context
     * @param v
     */
    public static void hideSoftKeyboardEvennotActive(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (v != null) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 在系统级别隐藏键盘
     *
     * @param activity
     */
    public static void hideSoftKeyboardSystem(Activity activity) {
        // 隐藏软键盘
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        activity.getWindow().getAttributes().softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
    }

    /**
     * 检测键盘是否展开
     *
     * @param activity
     * @return
     */
    public static boolean checkSoftKeyboardShowing(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
            return true;
        }
        return false;
    }
}
