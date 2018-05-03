package com.qdzl;

import android.app.Application;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;

import com.qdzl.bean.Config;
import com.qdzl.socket.SocketService;
import com.qdzl.utils.L;
import com.qdzl.utils.ToastUtil;

import java.util.List;

/**
 * Created by QDZL on 2017/11/29.
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
        ToastUtil.init(this);
        L.DEBUG = true;
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        Log.i("wwwwww", width + "----" + height);
        app = this;
//        DBUtil.initDB(this);

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

    public static void sendMessage(Integer what) {
        Message message = new Message();
        message.what = what;
        myHandler.sendMessage(message);
    }

    public static Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtil.show("Socket连接失败！10秒后自动重新连接...");
                    break;
                case 2:
                    ToastUtil.show(App.getConfig().getServerip() + ":" + App.getConfig().getSocketport()+" 连接成功！");
                    break;
            }
            super.handleMessage(msg);
        }
    };
}