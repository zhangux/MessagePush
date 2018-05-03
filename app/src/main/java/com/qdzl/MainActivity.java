package com.qdzl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.qdzl.bean.RequestData;
import com.qdzl.bean.Result;
import com.qdzl.client.ClientManager;
import com.qdzl.service.SocketService;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private WebView wvWeb;

    public static String MAC;
    public static boolean isCon;
    private TextView txtMsg;
    //消息提示显示时间
    private int time=0;
    //从失败到成功时刷新标记 0 请求成功 1已成功加载 -1失败
    private int count=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MAC = getWifiMac(this);
        bind();
        init();
        SocketService.startService("192.168.3.11", "9090");

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (isCon) {
                        if(count!=1){
                            count=0;
                        }
                        time=0;
                        txtMsg.post(new Runnable() {
                            @Override
                            public void run() {
                                txtMsg.setText("正在连接服务器...");
                                txtMsg.setText("连接成功！");
                            }
                        });
                        Log.e("aaaa", "----|true|----");
                        RequestData data = new RequestData();
                        data.setAct(3);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("mac", MAC);
                        map.put("projectname", "Light_Push");
                        map.put("lightid", "");
                        data.setData(map);
                        ClientManager.getInstance().sendMessage(JSON.toJSONString(data));
                    } else {
                        count=-1;
                        time++;
                        txtMsg.post(new Runnable() {
                            @Override
                            public void run() {
                                txtMsg.setText("正在连接服务器...");
                                if(time%10>=0&&time%10<3){
                                    txtMsg.setText("连接服务器失败！");
                                }
                            }
                        });
//                        Log.e("aaaa", "----|false|----");
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        wvWeb.getSettings().setJavaScriptEnabled(true);
        wvWeb.getSettings().setBlockNetworkImage(true);
        wvWeb.requestFocus();
    }

    private void bind() {
        wvWeb = (WebView) this.findViewById(R.id.video_webview);
        txtMsg = (TextView) this.findViewById(R.id.txt_msg);
    }

    private void init() {
        new TimeThread().start();
    }

    public void webVebInit(Map<String, Object> res) {
        Log.e("bbbb", "-----" + res.get("url"));
        wvWeb.loadUrl(res.get("url").toString());

//        wvWeb.setWebViewClient(new WebViewClient() {
//            public void onPageFinished(WebView view, String url) {
//                wvWeb.loadUrl("javascript:(function() { " +
//                        "var divs = document.getElementsByTagName('div');" +
//                        "divs[0].setAttribute('style','position: fixed; width: 100%; height: 100%');" +
//                        "var videos = document.getElementsByTagName('video');" +
//                        "videos[0].setAttribute('width','100%');" +
//                        "videos[0].setAttribute('height','100%');" +
//                        "videos[0].setAttribute('style', 'object-fit: fill');" +
//                        "videos[0].play();" +
//                        "})()");
              /*  wvWeb.getSettings().setBlockNetworkImage(false);
                wvWeb.loadUrl("javascript:(function() { " +
                        "var videos = document.getElementsByTagName('video');" +
                        "videos[0].play();" +
                        "})()");*/
//                wvWeb.loadUrl("javascript:play();");
//            }
//        });
    }
    class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    Thread.sleep(1000);
                    if(count==1){
                        continue;
                    }
                    if(count==0){
                        if (Result.data != null) {
                            count=1;
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
    //                        return;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    webVebInit(Result.data);
                    break;
            }
        }
    };

//    public String phoneIp() {
//        //获取wifi服务
//        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        //判断wifi是否开启
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int ipAddress = wifiInfo.getIpAddress();
//        String ip = intToIp(ipAddress);
//        Log.e("aaaa", "---ip---" + ip);
//        return ip;
//    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    public static String getWifiMac(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String str = info.getMacAddress();
        if (str == null) str = "";
        return str;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        System.exit(0);
        return true;
    }


}
