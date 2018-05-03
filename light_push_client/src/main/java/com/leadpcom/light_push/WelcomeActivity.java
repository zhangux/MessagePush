package com.leadpcom.light_push;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.leadpcom.light_push.core.App;
import com.leadpcom.light_push.entity.Config;
import com.leadpcom.light_push.util.Mac;
import com.leadpcom.light_push.util.T;

/**
 * Created by zz on 2017/3/14.
 */

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout tilLightId;
    private TextInputLayout tilProjectName;
    private TextInputLayout tilHost;
    private TextInputLayout tilsocketPort;
    private TextInputLayout tilserverPort;
    private SharedPreferences sp;
    private TextView text;
    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tilHost = (TextInputLayout) findViewById(R.id.host);
        tilLightId = (TextInputLayout) findViewById(R.id.light_id);
        tilProjectName = (TextInputLayout) findViewById(R.id.project_name);
        tilsocketPort = (TextInputLayout) findViewById(R.id.socketport);
        tilserverPort = (TextInputLayout) findViewById(R.id.serverport);
        text = (TextView) findViewById(R.id.text);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        tilLightId.getEditText().setText(sp.getString("lightid", "415"));
        tilProjectName.getEditText().setText(sp.getString("projectname", "Light_Push"));
        tilHost.getEditText().setText(sp.getString("host", "192.168.1.50"));
        tilsocketPort.getEditText().setText(sp.getString("socketPort", "9090"));
        tilserverPort.getEditText().setText(sp.getString("serverPort", "8080"));
        text.setText(text.getText() + "\r\n" + Mac.getLocalMacAddressFromWifiInfo(this));
        app = App.getApp();
    }

    @Override
    public void onClick(View view) {
        String lightId = tilLightId.getEditText().getText().toString().trim();
        String projectName = tilProjectName.getEditText().getText().toString().trim();
        String host = tilHost.getEditText().getText().toString().trim();
        String socketport = tilsocketPort.getEditText().getText().toString().trim();
        String serverPort = tilserverPort.getEditText().getText().toString().trim();
        String mac = text.getText().toString().trim();
        if (TextUtils.isEmpty(lightId) || "灯号".equals(lightId)) {
            T.show("请设置灯号");
            return;
        }
        if (TextUtils.isEmpty(projectName)) {
            T.show("请设置项目名");
            return;
        }
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
        e.putString("lightid", lightId);
        e.putString("projectname", projectName);
        e.putString("host", host);
        e.putString("socketport", socketport);
        e.putString("serverPort", serverPort);
        e.apply();
        Config c = new Config(host, socketport, serverPort, lightId, projectName, mac);
        app.setConfig(c);
        MainActivity.startActivity(this);
        finish();
    }
}
