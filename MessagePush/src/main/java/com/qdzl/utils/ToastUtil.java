package com.qdzl.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by xjm on 2016/7/28.
 */
public class ToastUtil {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void show(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void show(@StringRes int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }
}
