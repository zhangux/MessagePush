package com.qdzl.service;

import android.content.ContentProvider;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.qdzl.MainActivity;
import com.qdzl.bean.RequestData;
import com.qdzl.bean.Result;
import com.qdzl.client.ClientManager;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QDZL on 2017/12/18.
 */
public class SocketService {
    public static Map<String,Object> res;
    private static ClientManager.OnConnectedListener connectedLis = new ClientManager.OnConnectedListener() {
        @Override
        public void onConnected() {
            MainActivity.isCon=true;
            Log.e("aaaa", "-----Connected-----");
            RequestData data=new RequestData();
            data.setAct(1);
            Map<String, String> map = new HashMap<String, String>();
            map.put("mac", MainActivity.MAC);
            map.put("projectname","Light_Push");
            map.put("lightid", "");
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
            Result.data=JSON.parseObject(msg);
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
