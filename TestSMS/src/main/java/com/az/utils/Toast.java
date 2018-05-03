package com.az.utils;

import android.content.Context;

/**
 * Created by QDZL on 2018/4/25.
 */

public class Toast {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }
    public static void show(String msg){
        android.widget.Toast.makeText(mContext,msg, android.widget.Toast.LENGTH_SHORT).show();
    }
}