package com.leadpcom.light_push_frame.util;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.leadpcom.light_push_frame.core.App;


/**
 * Created by yjh on 2017/3/15.
 */

public class T {
    public static void show(String text) {
        Toast.makeText(App.getApp(), text, Toast.LENGTH_SHORT).show();
    }

    public static void show(@StringRes int text) {
        Toast.makeText(App.getApp(), text, Toast.LENGTH_SHORT).show();
    }
}
