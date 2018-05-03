package com.qdzl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.qdzl.bean.Config;
import com.qdzl.utils.Mac;
import com.qdzl.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz on 2017/3/14.
 */

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    //    private TextInputLayout tilLightId;
//    private TextInputLayout tilProjectName;
    private TextInputLayout tilHost;
    private TextInputLayout tilsocketPort;
    private TextInputLayout tilserverPort;
    private SharedPreferences sp;
    private TextView text;
    private App app;

    private Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tilHost = (TextInputLayout) findViewById(R.id.host);
//        tilLightId = (TextInputLayout) findViewById(R.id.light_id);
//        tilProjectName = (TextInputLayout) findViewById(R.id.project_name);
        tilsocketPort = (TextInputLayout) findViewById(R.id.socketport);
        tilserverPort = (TextInputLayout) findViewById(R.id.serverport);
        text = (TextView) findViewById(R.id.text);
        spinner = findViewById(R.id.pro_name);
        List<String> resource = new ArrayList<>();
        resource.add("海淀科技中心");
        resource.add("全电智领");
        resource.add("全电智领2");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resource);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
//        tilLightId.getEditText().setText(sp.getString("lightid", "415"));
//        tilProjectName.getEditText().setText(sp.getString("projectname", "Light_Push"));
        tilHost.getEditText().setText(sp.getString("host", "192.168.3.11"));
        tilsocketPort.getEditText().setText(sp.getString("socketPort", "9090"));
        spinner.setSelection(sp.getInt("spinnerId", 0), true);
        tilserverPort.getEditText().setText(sp.getString("serverPort", "8080"));

//        text.setText(text.getText() + "\r\n" + Mac.getLocalMacAddressFromWifiInfo(this));
        app = App.getApp();
    }

    @Override
    public void onClick(View view) {
        //        String lightId = tilLightId.getEditText().getText().toString().trim();
//        String projectName = tilProjectName.getEditText().getText().toString().trim();
        String host = tilHost.getEditText().getText().toString().trim();
        String socketport = tilsocketPort.getEditText().getText().toString().trim();
        String serverPort = tilserverPort.getEditText().getText().toString().trim();
        String mac = text.getText().toString().trim();
//        if (TextUtils.isEmpty(lightId) || "灯号".equals(lightId)) {
//            T.show("请设置灯号");
//            return;
//        }
//        if (TextUtils.isEmpty(projectName)) {
//            T.show("请设置项目名");
//            return;
//        }
        if (TextUtils.isEmpty(host)) {
            T.show("请设置服务器ip");
            return;
        }
        if (TextUtils.isEmpty(socketport)) {
            T.show("请设置Socket服务端口");
            return;
        }
        if (TextUtils.isEmpty(serverPort)) {
            T.show("请设置sever服务端口");
            return;
        }
        SharedPreferences.Editor e = sp.edit();
//        e.putString("lightid", lightId);
//        e.putString("projectname", projectName);
        e.putString("host", host);
        e.putString("socketPort", socketport);

        e.putInt("spinnerId", (int) spinner.getSelectedItemId());
        e.putString("spinnerValue", spinner.getSelectedItem().toString());

        e.putString("serverPort", serverPort);
        e.apply();
        Config c = new Config(host, socketport, null, null, null, mac);
        app.setConfig(c);
        switch (view.getId()) {
            case R.id.start:

//        MainActivity.startActivity(this);
                WineActivity.startActivity(this);
//        finish();
                break;
            case R.id.start_new:
                WineActivityNew.startActivity(this);
                break;
            case R.id.start_new_2:
                this.finish();
                WinesActivity.startActivity(this);
                break;
        }

    }
}
