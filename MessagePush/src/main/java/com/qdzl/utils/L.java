package com.qdzl.utils;

import android.util.Log;

/**
 *   Log 日志
 * Created by xjm on 2016/7/28.
 */
public class L {
    private static String TAG = "mylog";
    public static boolean DEBUG = true;

    public static void setTAG(String tag) {
        TAG = tag;
    }

    public static void e(String msg) {
        if (DEBUG)
            Log.e(TAG, msg);
    }

    public static void e(String msg, Throwable t) {
        if (DEBUG)
            Log.e(TAG, msg, t);
    }

    public static void w(String msg) {
        if (DEBUG)
            Log.w(TAG, msg);
    }

    public static void v(String msg) {
        if (DEBUG)
            Log.v(TAG, msg);
    }

    public static void i(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }
}
