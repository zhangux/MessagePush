package com.qdzl;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.qdzl.adapter.MyAdapter;
import com.qdzl.bean.RequestData;
import com.qdzl.bean.Result;
import com.qdzl.fragment.FragmentMap1;
import com.qdzl.fragment.FragmentMap2;
import com.qdzl.fragment.FragmentMap3;
import com.qdzl.fragment.FragmentMap4;
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

public class MainActivity extends FragmentActivity {
    public static String UDID;
    public static String MODEL;

    private RadioGroup rg;
    private ViewPager vp;
    private FragmentMap1 fragmentMap1;
    private FragmentMap2 fragmentMap2;
    private FragmentMap3 fragmentMap3;
    private FragmentMap4 fragmentMap4;


    private static SharedPreferences sp;

    public static String light1 = "9A";
    public static String light2 = "9B";

    private int t;

    private static String lightNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1、 去掉状态栏！！！！！！！！！！
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //2、隐藏状态栏！！！！！！！！！！！！
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        UDID = getUdid(this);
        MODEL = Build.MODEL;
        init();
        //SOCKET
//        if (App.getConfig() != null) {
//            Toast.makeText(MainActivity.this, App.getConfig().getServerip(), Toast.LENGTH_SHORT).show();
//            SocketService.startService(App.getConfig().getServerip(), App.getConfig().getSocketport());
//        }
    }

    private void init() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        t = sp.getInt("spinnerId", 0);
        Log.e("aaaa", "--------------" + t);
        if (t != 0) {
            if (t == 1) {
                light1 = "9Y";
                light2 = "9Z";
            } else if (t == 2) {
                light1 = "9R";
                light2 = "9T";
            }
        } else {
            light1 = "9A";
            light2 = "9B";
        }

        rg = findViewById(R.id.main_rg);
        rg.setOnCheckedChangeListener(checkedChangeLis);

        vp = findViewById(R.id.main_vp);

        fragmentMap1 = new FragmentMap1();
        fragmentMap2 = new FragmentMap2();
        if (t == 0) {
            fragmentMap3 = new FragmentMap3();
            fragmentMap4 = new FragmentMap4();
        }
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(fragmentMap1);
        fragments.add(fragmentMap2);
        if (t == 0) {
            fragments.add(fragmentMap3);
            fragments.add(fragmentMap4);
        }

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(pageChangeList);

        //HTTP
        String email = MainActivity.UDID;
        String nickname = MainActivity.MODEL;
//        String projectname = sp.getString("spinnerValue", null);

        String host = sp.getString("host", "192.168.3.11");
        String port = sp.getString("serverPort", "8080");
        String url = "http://" + host + ":" + port + "/MayaCloud/userLight/save.do?email=" + email + "&nickname=" + nickname;
        HttpUtils.get(url, new JsonCallback<Result<String>>() {
            @Override
            public void onSuccess(Call call, Result<String> data) {
                if (!data.state) {
                    T.show(data.msg);
                }
            }
        });
    }

    ViewPager.OnPageChangeListener pageChangeList = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton rb = (RadioButton) rg.getChildAt(position);
            rb.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    RadioGroup.OnCheckedChangeListener checkedChangeLis = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.main_gb_1:
                    vp.setCurrentItem(0);
                    break;
                case R.id.main_gb_2:
                    vp.setCurrentItem(1);
                    break;
                case R.id.main_gb_3:
                    if (t == 0)
                        vp.setCurrentItem(2);
                    break;
                case R.id.main_gb_4:
                    if (t == 0)
                        vp.setCurrentItem(3);
                    break;
            }
        }
    };

    public static void on(String lightNum) {
        MainActivity.lightNum = lightNum;
        //SOCKET
//        RequestData data = new RequestData();
//        data.setAct(2);
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("lightNum", lightNum);
//        map.put("account", MainActivity.UDID);
//        map.put("nickname", MainActivity.MODEL);
//        map.put("password", "123123");
//        map.put("projectname", sp.getString("spinnerValue", ""));
//
//        data.setData(map);
//        ClientManager.getInstance().sendMessage(JSON.toJSONString(data));

        //HTTP
        String email = MainActivity.UDID;
        String nickname = MainActivity.MODEL;
        String projectname = sp.getString("spinnerValue", null);

        String host = sp.getString("host", "192.168.3.11");
        String port = sp.getString("serverPort", "8080");
        String url = "http://" + host + ":" + port + "/MayaCloud/userLight/save.do?email=" + email + "&nickname=" + nickname + "&lightNum=" + lightNum + "&projectname=" + projectname;

        HttpUtils.get(url, new JsonCallback<Result<String>>() {
            @Override
            public void onSuccess(Call call, Result<String> data) {
                if (!data.state) {
                    T.show(data.msg);
                }
            }
        });
    }

    public static String getUdid(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }

    public static String getWifiMac(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String str = info.getMacAddress();
        if (str == null) str = "";
        return str;
    }

    public static void startActivity(Context context) {
        Intent in = new Intent(context, MainActivity.class);
        context.startActivity(in);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        关闭socket
//        SocketService.stop();

        if (MainActivity.lightNum == null) {
            return;
        }
        String host = sp.getString("host", "192.168.3.11");
        String port = sp.getString("serverPort", "8080");
        String url = "http://" + host + ":" + port + "/MayaCloud/emp/empLogout.do?email=" + MainActivity.UDID + "&lightNum=" + MainActivity.lightNum + "&projectname=" + sp.getString("spinnerValue", "");
        HttpUtils.get(url, new JsonCallback<Result<String>>() {
            @Override
            public void onSuccess(Call call, Result<String> data) {
                if (data.state) {
                    T.show("退出！");
                } else {
                    T.show(data.msg);
                }
            }
        });
    }
}
