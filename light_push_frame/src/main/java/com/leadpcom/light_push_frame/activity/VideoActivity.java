package com.leadpcom.light_push_frame.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import com.leadpcom.light_push_frame.R;
import com.leadpcom.light_push_frame.core.App;
import com.leadpcom.light_push_frame.entity.Config;
import com.leadpcom.light_push_frame.entity.FrameInfo;
import com.leadpcom.light_push_frame.socket.SocketService;
import com.leadpcom.light_push_frame.util.Mac;

import java.util.List;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by yjh on 2018/1/2.
 */

public class VideoActivity extends AppCompatActivity {
    private VideoView myvideo;
    private App app;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        myvideo = (VideoView) findViewById(R.id.myVideo);
        myvideo.setVideoPath(App.assetPath + "kepu.mp4");
        Config c = new Config("192.168.3.11", "9090", "8080", "", "Light_Push", Mac.getLocalMacAddressFromWifiInfo(this));
        app.setConfig(c);
        if (c != null) {
            Toast.makeText(VideoActivity.this, c.getServerip(), Toast.LENGTH_SHORT).show();
            SocketService.startService(c.getServerip(), c.getSocketport());
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void event(List<FrameInfo> infos) {
        if (infos != null && infos.size() > 0) {
            if (!"".equals(infos.get(0).getFileurl())) {
                String http = "http://" + App.getConfig().getServerip() + ":" + App.getConfig().getServerPort() + "/";
                Log.i("ppp", http + infos.get(0).getFileurl());
                myvideo.setVideoPath(http + infos.get(0).getFileurl());
            }
        }
    }

}
