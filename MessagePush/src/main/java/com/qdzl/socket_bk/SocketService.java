package com.qdzl.socket_bk;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.qdzl.MainActivity;
import com.qdzl.bean.RequestData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by YJH on 2017/3/15.
 */

public class SocketService {
    private static ClientManager.OnConnectedListener connectedLis = new ClientManager.OnConnectedListener() {
        @Override
        public void onConnected() {
            ClientManager.isCon=true;
//            MainActivity.isCon=true;
            Log.e("aaaa", "-----Connected-----");
            RequestData data = new RequestData();
            data.setAct(1);
            Map<String, String> map = new HashMap<String, String>();
            map.put("account", MainActivity.UDID);
            map.put("nickname", MainActivity.MODEL);
            map.put("password", "123123");
            data.setData(map);
            ClientManager.getInstance().sendMessage(JSON.toJSONString(data));
        }

        @Override
        public void onDisconnected() {
            Log.e("aaaa", "-----Disconnected-----");
        }
    };

    private static ClientManager.OnReceivedMessageListener messageLis = new ClientManager.OnReceivedMessageListener() {
        @Override
        public void onReceivedMessage(String msg) {
            Log.e("aaaa", "---msg----" + msg);
        }
    };

    public static void startService(String host, String port) {
        ClientManager manager = ClientManager.getInstance();
        manager.setOnConnectedListener(connectedLis);
        manager.setOnReceivedMessageListener(messageLis);
        manager.starts(host, Integer.parseInt(port));
    }

    public static void stop() {
        try {
            ClientManager.getInstance().stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
