package com.qdzl

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 * Created by QDZL on 2018/2/6.
 */
//类
class HelloActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotlin);
        var llMain: LinearLayout? = null;
        llMain = findViewById(R.id.ll_main);
        Log.e("aaaa", "------llMain.childCount:${llMain.childCount}");
        var ll: LinearLayout = llMain.getChildAt(1) as LinearLayout;

        var imgClick:(View) -> Unit={v -> viewClick(v)};
        llMain.setOnClickListener({ v -> viewClick(v) });
    }

    private fun viewClick(v:View){

    }
}
