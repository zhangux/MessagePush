package com.qdzl.fx;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by QDZL on 2018/3/16.
 */

public class MoveActivity extends Activity {
    private ImageView img_people;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        img_people = findViewById(R.id.img_people);

        img_people.post(new Runnable() {
            @Override
            public void run() {
                Log.e("aaaa", "--------X:" + img_people.getX() + ",Y:" + img_people.getY() + "Width:" + img_people.getMeasuredWidth() + ",Height:" + img_people.getMeasuredHeight());

            }
        });
//        Animation animation=R.anim.
//        img_people.startAnimation();


//        double x = img_people.getX();
//        double y = img_people.getY();
//
//        int width = 40;
//        int height = 40;
//        img_people.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @Override
//            public void onGlobalLayout() {
//                // TODO Auto-generated method stub
//                img_people.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//
//                Log.e("aaaa", img_people.getWidth()+","+img_people.getHeight());
//            }
//        });
        //        Log.e("aaaa", "--------X:" + x + ",Y:" + y + ",Width:" + width + ",Height:" + height);
//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        img_people.measure(w,h);
//        int height = img_people.getMeasuredHeight();
//        int width = img_people.getMeasuredWidth();
//        Log.e("aaaa", "Width:" + width + ",Height:" + height);
    }
}
