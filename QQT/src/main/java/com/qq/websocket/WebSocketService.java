package com.qq.websocket;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.qq.bean.WebSockets;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import de.tavendo.autobahn.WebSocketOptions;

/**
 * Created by wxs on 16/8/17.
 */
public class WebSocketService extends Service {

    private static final String TAG = "websocketservice";

    public static final String WEBSOCKET_ACTION = "WEBSOCKET_ACTION";

    private BroadcastReceiver connectionReceiver;
    private static boolean isClosed = true;
    private static WebSocketConnection webSocketConnection;
    private static WebSocketOptions options = new WebSocketOptions();
    private static boolean isExitApp = false;
    //    private static String websocketHost = "ws://192.168.3.11:8080/WineCabinet/websocket";
//    private static String websocketHost = "ws://123.207.162.227:8080/WS/websockets/" + MainActivity.getNewMac();
    private static String websocketHost = "ws://123.207.162.227:8080/WS/websockets/" + "zx";
//    private static String websocketHost = "ws://192.168.3.11:8080/ws/websocket/socketServer";

    private String message = "";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (connectionReceiver == null) {
            connectionReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo == null || !networkInfo.isAvailable()) {
                        Toast.makeText(getApplicationContext(), "网络已断开，请重新连接", Toast.LENGTH_SHORT).show();
                    } else {
                        if (webSocketConnection != null) {
                            webSocketConnection.disconnect();
                        }
                        if (isClosed) {
                            webSocketConnect();
                        }
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(connectionReceiver, intentFilter);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void closeWebsocket(boolean exitApp) {
        isExitApp = exitApp;
        if (webSocketConnection != null && webSocketConnection.isConnected()) {
            webSocketConnection.disconnect();
            webSocketConnection = null;
        }
    }

    public void webSocketConnect() {
        webSocketConnection = new WebSocketConnection();
        try {
            webSocketConnection.connect(websocketHost, new WebSocketHandler() {
                //websocket启动时候的回调
                @Override
                public void onOpen() {
                    Log.e(TAG, "open");
                    isClosed = false;
                    sendMessage("连接成功！！！！");
                }

                //websocket接收到消息后的回调
                @Override
                public void onTextMessage(String payload) {
                    Log.e(TAG, "payload = " + payload);
                    message = payload;
                    sendMessage(message);
                }

                //websocket关闭时候的回调
                @Override
                public void onClose(int code, String reason) {
                    isClosed = true;
                    Log.e(TAG, "code = " + code + " reason = " + reason);
                    sendMessage("连接成功！！！！");
                    switch (code) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3://手动断开连接
//                            if (!isExitApp) {
//                                webSocketConnect();
//                            }
                            break;
                        case 4:
                            break;
                        /**
                         * 由于我在这里已经对网络进行了判断,所以相关操作就不在这里做了
                         */
                        case 5://网络断开连接
//                            closeWebsocket(false);
//                            webSocketConnect();
                            break;
                    }
                }
            }, options);
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
//        sendMessage(message);
    }

    private void sendMessage(String payload) {
        //创建Intent
        Intent intent = new Intent(WEBSOCKET_ACTION);
        intent.putExtra("message", payload);
        //发送广播
        sendBroadcast(intent);
        Log.e("sendmessage", "ddddddddddddddddddddddddddddddddddd");
    }

    private static String username = "123456789";
    private String type = "1";

    public static void sendMsg(String s) {
        Log.e(TAG, "sendMsg = " + s);
        //{"msg":"亚希！","type":1,"username":"0.45276066296901996"}
        WebSockets webSockets = new WebSockets(1, username, s);
        String msg = JSON.toJSONString(webSockets);
        if (!TextUtils.isEmpty(s))
            if (webSocketConnection != null) {
                webSocketConnection.sendTextMessage(msg);
            }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
        }
    }
}
