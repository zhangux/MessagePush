package com.qdzl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.qdzl.adapter.WineAdapter;
import com.qdzl.bean.RequestData;
import com.qdzl.bean.Result;
import com.qdzl.network.HttpUtils;
import com.qdzl.network.JsonCallback;
import com.qdzl.socket.ClientManager;
import com.qdzl.socket.SocketService;
import com.qdzl.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by QDZL on 2018/2/8.
 */

public class WineActivityNew extends FragmentActivity {
//    private Button btnSub;
//    private Button btnF;

    public static SharedPreferences sp;
    //    private Spinner spData;
    private List<String> pdStrLis;
    private WineAdapter wineAdapter;
    private RecyclerView rvPd;

    private Button btj1;
    private Button btj2;
    private Button btClear;

    StringBuilder pd1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_new);
        //SOCKET
        if (App.getConfig() != null) {
            Toast.makeText(WineActivityNew.this, App.getConfig().getServerip() + ":" + App.getConfig().getSocketport(), Toast.LENGTH_SHORT).show();
            SocketService.startService(App.getConfig().getServerip(), App.getConfig().getSocketport());
        }
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String p1 = sp.getString("pd1", "0000000000000000000000000000000000000000000000000000000000000000");
        pd1 = new StringBuilder(p1);
        init();
    }

    //EF000100FFF01
    public void init() {
        rvPd = findViewById(R.id.rv_pd);

        btj1 = findViewById(R.id.bt_j_1);
        btj2 = findViewById(R.id.bt_j_2);
        btClear = findViewById(R.id.bt_clear);

        btj1.setOnClickListener(onClickLis);
        btj2.setOnClickListener(onClickLis);
        btClear.setOnClickListener(onClickLis);

        pdStrLis = new ArrayList<>();
        for (int i = 1; i <= 48; i++) {
            pdStrLis.add(i + "");
        }
        wineAdapter = new WineAdapter(pdStrLis, this,null);
        rvPd.setLayoutManager(new LinearLayoutManager(this));
        rvPd.setAdapter(wineAdapter);
        wineAdapter.setOnItemClickListener(new WineAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, Integer isH) {
                Log.e("aaaa", "========" + position + "=====" + isH);
//                if (ClientManager.isCon) {
                sendPD(isH, position);
//                }
            }
        });
    }

    public static void startActivity(Context context) {
        Intent in = new Intent(context, WineActivityNew.class);
        context.startActivity(in);
    }

    View.OnClickListener onClickLis = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_j_1:
                    sendPD2(1);
                    break;
                case R.id.bt_j_2:
                    sendPD2(2);
                    break;
                case R.id.bt_clear:
                    sendClear();
                    break;
            }

        }
    };

    private void sendClear() {
        SharedPreferences.Editor editor = WineActivityNew.sp.edit();
        editor.remove("mapState");
        editor.apply();
        pd1 = new StringBuilder("0000000000000000000000000000000000000000000000000000000000000000");
        wineAdapter.clear();

//        SharedPreferences.Editor editor = WineActivity.sp.edit();


        String host = sp.getString("host", "192.168.3.11");
        String port = sp.getString("serverPort", "8080");
        String url = "http://" + host + ":" + port + "/WineCabinet/containerGoods/updateClear.do?cid=" + 4 + "&positionstate=" + 0;

        HttpUtils.get(url, new JsonCallback<Result<String>>() {
            @Override
            public void onSuccess(Call call, Result<String> data) {
                if (!data.state) {
                    T.show(data.msg);
                }
            }
        });
    }

    private void sendPD2(int i) {
        String pdStr = "10000000001_123_A";
        if (i == 1) {
            pdStr = "10000000001_123_A";
        } else {
            pdStr = "01000000001_123_A";
        }
        RequestData data = new RequestData();
        data.setAct(3);
        Map<String, String> map = new HashMap<String, String>();
        map.put("lightid", pdStr);
//        map.put("devicemac", "1111");
        map.put("projectname", sp.getString("spinnerValue", ""));

        data.setData(map);
        ClientManager.getInstance().sendMessage(JSON.toJSONString(data));
    }

    private void sendPD(Integer isH, Integer index) {
        String pdStr = "000100";
        StringBuilder pd = new StringBuilder();
        if (index <= 23) {
            pdStr = "000201";
        } else {
            pdStr = "000202";
        }
        pd=pd1;
        for (int i = 0; i < pd.length(); i++) {
            if (i == index) {
                int p = i;
                if (isH == 1) {
                    pd.replace(p, p + 1, "1");
                } else if (isH == 0) {
                    pd.replace(p, p + 1, "0");
                } else {
                    buy(4, 2, p + 1);
                    return;
                }
            }
        }
        SharedPreferences.Editor editor = WineActivityNew.sp.edit();
        editor.putString("pd1", pd.toString());
        editor.apply();

        Log.e("aaaa", "----------------" + pd.toString());
        String pd2 = "";
        for (int i = 0; i < pd.length(); i += 4) {
            String s = pd.substring(i, i + 4);
            pd2 += Integer.toHexString(Integer.parseInt(s, 2));
        }
        Integer count10 = 0;
        // EF000100100010000000001003
        for (int i = 0; i < pd2.length(); i += 2) {
            String s16 = pd2.substring(i, i + 2);
            Integer s10 = Integer.parseInt(s16, 16);
            count10 += s10;
        }
        String count16 = count10.toHexString(count10);
        if (count10 < 10) {
            count16 = "0" + count10.toHexString(count10);
        }
        if (count16.length() == 1) {
            count16 = "0" + count16;
        }
        if (count16.length() != 2) {
            count16 = count16.substring(count16.length() - 2, count16.length());
        }
        pdStr = "EF" + pdStr + pd2 + count16;

        Log.e("aaaa", "------" + pdStr);

        RequestData data = new RequestData();
        data.setAct(3);
        Map<String, String> map = new HashMap<String, String>();
        map.put("lightid", pdStr);
        map.put("devicemac", "1111");
        map.put("projectname", sp.getString("spinnerValue", ""));

        data.setData(map);
        ClientManager.getInstance().sendMessage(pdStr);
    }

    public void buy(Integer cid, Integer layer, Integer position) {
        Integer p=position%12;
        Integer index=(position/12)+1;
        if(p==0){
            p=12;
            index=index-1;
        }
        //cid=4&layer=2&position=1&pdposition=25&index=3
        //cid, layer, position,pdposition,index

        String host = sp.getString("host", "192.168.3.11");
        String port = sp.getString("serverPort", "8080");
        String url = "http://" + host + ":" + port + "/WineCabinet/containerGoods/updateBuy.do?cid=" + cid + "&layer="
                + layer + "&position=" + p + "&pdposition="+position+"&index=" + index;

        HttpUtils.get(url, new JsonCallback<Result<String>>() {
            @Override
            public void onSuccess(Call call, Result<String> data) {
                if (!data.state) {
                    T.show(data.msg);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        SocketService.isClose=true;
        SocketService.stop();
        super.onDestroy();
    }
}

