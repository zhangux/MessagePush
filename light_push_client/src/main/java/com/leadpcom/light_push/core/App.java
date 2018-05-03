package com.leadpcom.light_push.core;

import android.app.Application;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.leadpcom.light_push.entity.Config;
import com.leadpcom.light_push.socket.SocketService;
import com.leadpcom.light_push.util.DBUtil;

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

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        Log.i("wwwwww", width + "----" + height);
        app = this;
        DBUtil.initDB(this);

    }

    public static Config getConfig() {
        return config;
    }

    public void setConfig(Config c) {
        this.config = c;
    }

    @Override
    public void onTerminate() {
        SocketService.stop();
        super.onTerminate();
    }

}
