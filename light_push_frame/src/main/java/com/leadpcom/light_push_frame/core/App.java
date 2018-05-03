package com.leadpcom.light_push_frame.core;

import android.app.Application;

import com.leadpcom.light_push_frame.entity.Config;
import com.leadpcom.light_push_frame.socket.SocketService;
import com.leadpcom.light_push_frame.util.DBUtil;

import java.util.List;

/**
 * Created by zz on 2017/3/15.
 */

public class App extends Application {

    private static App app;
    private static Config config;
    List<Config> configs;
    public static int width = 2560;
    public static int height = 1080;
    public static String assetPath="file:///android_asset/video/";

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Resources resources = this.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        width = dm.widthPixels;
//        height = dm.heightPixels;
        app = this;
        DBUtil.initDB(this);
    }

    public static Config getConfig() {
        return config;
    }

    public static void setConfig(Config c) {
        config = c;
    }

    @Override
    public void onTerminate() {
        SocketService.stop();
        super.onTerminate();
    }
}
