package com.leadpcom.light_push.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zz on 2017/3/15.
 */

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override//去除手势滑动翻页
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
