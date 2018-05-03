package com.leadpcom.light_push.socket;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.leadpcom.light_push.MainActivity;
import com.leadpcom.light_push.core.App;
import com.leadpcom.light_push.entity.FrameInfo;
import com.leadpcom.light_push.util.DBUtil;

import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;

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
            JSONObject jo = new JSONObject();
            jo.put("act",1);
            jo.put("data",App.getConfig());
            ClientManager.getInstance().sendMessage(jo.toJSONString());
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
            List<FrameInfo> infos = JSON.parseObject(msg, new TypeReference<List<FrameInfo>>() {
            });
            if (infos == null || infos.isEmpty())
                return;
            try {
                ActiveAndroid.beginTransaction();
                DBUtil.clearImageByType(1);
                for (FrameInfo info : infos) {
                    info.setReserve(1);
                    info.save();
                }
                EventBus.getDefault().post(infos);
                ActiveAndroid.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ActiveAndroid.endTransaction();
            }
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
