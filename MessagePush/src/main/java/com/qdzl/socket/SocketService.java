package com.qdzl.socket;

import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.qdzl.App;
import com.qdzl.MainActivity;
import com.qdzl.WineActivity;
import com.qdzl.bean.RequestData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by YJH on 2017/3/15.
 */

public class SocketService {
    public static boolean isClose=false;
    private static ClientManager.OnConnectedListener connectedLis = new ClientManager.OnConnectedListener() {
        @Override
        public void onConnected() {
            ClientManager.isCon=true;
//            Toast.makeText(WineActivity.this, App.getConfig().getServerip() + ":" + App.getConfig().getSocketport(), Toast.LENGTH_SHORT).show();
//            MainActivity.isCon=true;
            App.sendMessage(2);
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
            Log.e("aaaa", "-----Disconnected-----"+SocketService.isClose);
            if (App.getConfig() != null&&!SocketService.isClose) {
                SocketService.startService(App.getConfig().getServerip(), App.getConfig().getSocketport());
            }
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

    /*

INSERT INTO `goods_record` VALUES
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10'),
(null, '5', '1', '2018-04-10 17:08:24', '2018-04-10')

    */

    public static void stop() {
        try {
            ClientManager.getInstance().stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
