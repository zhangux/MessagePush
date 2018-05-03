package com.leadpcom.light_push_frame.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.leadpcom.light_push_frame.R;
import com.leadpcom.light_push_frame.core.App;
import com.leadpcom.light_push_frame.entity.Config;
import com.leadpcom.light_push_frame.entity.FrameInfo;
import com.leadpcom.light_push_frame.socket.SocketService;
import com.leadpcom.light_push_frame.util.Mac;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private WebView wbHtml;
    private App app;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = App.getApp();
        wbHtml = (WebView) findViewById(R.id.main_wv);
        EventBus.getDefault().register(this);
        WebSettings settings = wbHtml.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setJavaScriptEnabled(true);//支持js
        settings.setPluginState(WebSettings.PluginState.ON);// 支持插件
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小  无效
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        wbHtml.setWebViewClient(webViewClient);
        wbHtml.setWebChromeClient(webChromeClient);
        wbHtml.loadUrl(App.assetPath + "1.html");
        Config c = new Config("192.168.3.3", "9090", "8080", "", "Light_Push", Mac.getLocalMacAddressFromWifiInfo(this));
        app.setConfig(c);
        if (c != null) {
            Toast.makeText(MainActivity.this, c.getServerip(), Toast.LENGTH_SHORT).show();
            SocketService.startService(c.getServerip(), c.getSocketport());
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void event(List<FrameInfo> infos) {
        if (infos != null && infos.size() > 0) {
            if (!"".equals(infos.get(0).getFileurl())) {
                String http = "http://" + App.getConfig().getServerip() + ":" + App.getConfig().getServerPort() + "/";
                Log.i("ppp", http + infos.get(0).getFileurl());
                wbHtml.loadUrl(http + infos.get(0).getFileurl());
            }
        }
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                //wbHtml.loadUrl("javascript:var timeId = setInterval(function(){if(document.getElementById(\"video\")) {clearInterval(timeId);document.getElementById(\"video\").play();document.getElementById(\"videoLayer\").style.display=\"none\";document.getElementById(\"videoPlay\").style.display=\"none\";}},1000)");
            } else {

            }
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

}
