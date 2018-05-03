package com.qdzl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qdzl.adapter.WineAdapter;
import com.qdzl.bean.Container;
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
 * Created by QDZL on 2018/4/10.
 */

public class WinesActivity extends FragmentActivity {
    private RadioGroup containerRg;
    private RadioGroup wineRg;

    Integer containerId = 0;
    Integer layerId = 0;
    //    private FragmentMap1 fragmentMap1;
//    private FragmentMap2 fragmentMap2;
//    private FragmentMap3 fragmentMap3;
//    private FragmentMap4 fragmentMap4;
    List<Container> containerList = new ArrayList<>();

    private RecyclerView rvPd;
    private WineAdapter wineAdapter;
    private Button btClear;

    public static SharedPreferences sp;
    //    private Spinner spData;
    private List<String> pdStrLis;


    StringBuilder pdSp = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wines);
        if (App.getConfig() != null) {
//            Toast.makeText(WineActivityNew.this, App.getConfig().getServerip() + ":" + App.getConfig().getSocketport(), Toast.LENGTH_SHORT).show();
            SocketService.startService(App.getConfig().getServerip(), App.getConfig().getSocketport());
        }
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        init();
    }

    private void init() {

        containerRg = findViewById(R.id.container_rg);
        wineRg = findViewById(R.id.wines_rg);
        rvPd = findViewById(R.id.rv_pd);
        btClear = findViewById(R.id.bt_clear);
        btClear.setOnClickListener(onClickLis);

        containerRg.setOnCheckedChangeListener(checkedChangeLis);

//        vp = findViewById(R.id.main_vp);

        String host = sp.getString("host", "192.168.3.11");
        String port = sp.getString("serverPort", "8080");
        String url = "http://" + host + ":" + port + "/WineCabinet/container/queryAll.do";

        HttpUtils.get(url, new JsonCallback<Result<List<Container>>>() {
            @Override
            public void onSuccess(Call call, Result<List<Container>> data) {
                if (!data.state) {
                    T.show(data.msg);
                    return;
                }
                containerList = data.rows;
                initTitle();
            }
        });


    }

    View.OnClickListener onClickLis = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_clear:
                    sendClear();
                    break;
            }

        }
    };

    private void initTitle() {
//        Log.e("aaaa", "---------" + containerList.size());
        for (int i = 0; i < containerList.size(); i++) {
            Container container = containerList.get(i);
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(container.getExplain());
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setTextColor(Color.parseColor("#008cff"));
            radioButton.setBackground(getResources().getDrawable(R.drawable.rg_checked_title));

            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            radioButton.setLayoutParams(layoutParams);
            containerRg.addView(radioButton);
        }

        pdStrLis = new ArrayList<>();
        wineAdapter = new WineAdapter(pdStrLis, this, "1-1");
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

        wineRg.setOnCheckedChangeListener(checkedChangeLis2);
        RadioButton rb = (RadioButton) containerRg.getChildAt(0);
        rb.setChecked(true);
    }

    RadioGroup.OnCheckedChangeListener checkedChangeLis2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            String temp = i + "";
            layerId = Integer.parseInt(temp.substring(temp.length() - 1, temp.length()));
            Log.e("aaaa", "------" + containerId + "设备，" + layerId + "层");

            String spStr = "container_" + containerId + "_layer_" + layerId;
            String p = sp.getString(spStr, "0000000000000000000000000000000000000000000000000000000000000000");
            pdSp = new StringBuilder(p);
//            pdStrLis=new ArrayList<>();
            if (containerId == 1 || containerId == 2) {
                wineAdapter.clearAll();
                for (int j = 1; j <= 24; j++) {
                    pdStrLis.add(j + "");
                }
                wineAdapter.setData(pdStrLis, containerId + "-" + layerId);
                rvPd.scrollToPosition(0);
            } else if (containerId == 3 || containerId == 4) {
                wineAdapter.clearAll();
                for (int j = 1; j <= 48; j++) {
                    pdStrLis.add(j + "");
                }
                wineAdapter.setData(pdStrLis, containerId + "-" + layerId);
                rvPd.scrollToPosition(0);
            }
        }
    };
    RadioGroup.OnCheckedChangeListener checkedChangeLis = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
//            Log.e("aaaa", "---------" + i);
            containerId = i;

            if (containerList == null || containerList.size() <= 0) {
                return;
            }
            Log.e("aaaa", "-------" + i);
            Container container = containerList.get(i - 1);
            wineRg.removeAllViews();
            for (int j = 0; j < container.getLayercount(); j++) {
//                Container container2 = containerList.get(j);
                RadioButton radioButton = new RadioButton(WinesActivity.this);
                String layer = containerId + "" + (j + 1);
                radioButton.setId(Integer.parseInt(layer));
                radioButton.setText((j + 1) + "");
                radioButton.setGravity(Gravity.CENTER);
                radioButton.setButtonDrawable(android.R.color.transparent);
                radioButton.setTextColor(Color.parseColor("#008cff"));
                radioButton.setBackground(getResources().getDrawable(R.drawable.rg_checked));

                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                radioButton.setLayoutParams(layoutParams);
                wineRg.addView(radioButton);
            }
            RadioButton rb = (RadioButton) wineRg.getChildAt(0);
            rb.setChecked(true);
        }
    };

    private void sendPD(Integer isH, Integer index) {
        String pdStr = "000100";
        StringBuilder pd = new StringBuilder();
        if (index <= 23) {
            pdStr = "0" + containerId + "0" + layerId + "01";
        } else {
            pdStr = "0" + containerId + "0" + layerId + "02";
        }
//        Log.e("aaaa","---index---"+index);
//        Log.e("aaaa","---isH---"+isH);
        pd = pdSp;
        for (int i = 0; i < pd.length(); i++) {
            if (i == index) {
                int p = i;
                if (isH == 1) {
                    pd.replace(p, p + 1, "1");
                } else if (isH == 0) {
                    pd.replace(p, p + 1, "0");
                } else {
                    if (isH == 3) {
                        isH = 0;
                    }
                    buy(containerList.get(containerId - 1).getId(), layerId, p + 1, isH);
                    return;
                }
            }
        }
        String spStr = "container_" + containerId + "_layer_" + layerId;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(spStr, pd.toString());
        editor.apply();

        Log.e("aaaa", "-------pd.toString---------" + pd.toString());
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

//        RequestData data = new RequestData();
//        data.setAct(3);
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("lightid", pdStr);
//        map.put("devicemac", "1111");
//        map.put("projectname", sp.getString("spinnerValue", ""));
//
//        data.setData(map);
        //发消息
        ClientManager.getInstance().sendMessage(pdStr);
    }

    private void sendClear() {
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < containerList.size(); i++) {
            for (int j = 0; j < containerList.get(i).getLayercount(); j++) {
                String containerLayer = (i + 1) + "-" + (j + 1);
                editor.remove("mapState" + containerLayer);
                String spStr = "container_" + (i + 1) + "_layer_" + (j + 1);
                editor.remove(spStr);
                editor.apply();
            }
            String host = sp.getString("host", "192.168.3.11");
            String port = sp.getString("serverPort", "8080");
            String url = "http://" + host + ":" + port + "/WineCabinet/containerGoods/updateClear.do?cid=" + containerList.get(i).getId() + "&positionstate=" + 0;

            HttpUtils.get(url, new JsonCallback<Result<String>>() {
                @Override
                public void onSuccess(Call call, Result<String> data) {
                    if (!data.state) {
                        T.show(data.msg);
                    }
                }
            });
        }
        pdSp = new StringBuilder("0000000000000000000000000000000000000000000000000000000000000000");
        wineAdapter.clear();
    }

    public void buy(Integer cid, Integer layer, Integer position, Integer state) {
        Integer p = position % 12;
        Integer index = (position / 12) + 1;
        if (p == 0) {
            p = 12;
            index = index - 1;
        }
        //cid=4&layer=2&position=1&pdposition=25&index=3
        //cid, layer, position,pdposition,index

        String host = sp.getString("host", "192.168.3.11");
        String port = sp.getString("serverPort", "8080");
        String url = "http://" + host + ":" + port + "/WineCabinet/containerGoods/updateGoodState.do?cid=" + cid + "&layer="
                + layer + "&position=" + p + "&pdposition=" + position + "&index=" + index + "&state=" + state;

        HttpUtils.get(url, new JsonCallback<Result<String>>() {
            @Override
            public void onSuccess(Call call, Result<String> data) {
                if (!data.state) {
                    T.show(data.msg);
                }
            }
        });
    }

    public static void startActivity(Context context) {
        Intent in = new Intent(context, WinesActivity.class);
        context.startActivity(in);
    }

    @Override
    protected void onDestroy() {
        SocketService.isClose = true;
        SocketService.stop();
        super.onDestroy();
        System.exit(0);
    }
}
