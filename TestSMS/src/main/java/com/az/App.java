package com.az;

import android.app.Application;

import com.az.utils.Toast;

/**
 * Created by QDZL on 2018/4/25.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.init(this);
//        MobSDK.init(this);
    }
}
