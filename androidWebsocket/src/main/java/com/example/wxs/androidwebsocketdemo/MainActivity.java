package com.example.wxs.androidwebsocketdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{


    private Button connectBtn;
    private Button disconnectBtn;
    private TextView messageTv;
    private EditText sendMsgEdit;
    private Button sendMsgBtn;
    private Intent websocketServiceIntent;
    WebSocketService service;
    private ScrollView svMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        websocketServiceIntent = new Intent(this, WebSocketService.class);
        startService(websocketServiceIntent);
        findViews();
        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connect_btn:
                service.webSocketConnect();
                break;

            case R.id.disconnect_btn:
                WebSocketService.closeWebsocket(false);
                break;

            case R.id.send_msg_btn:
                WebSocketService.sendMsg(sendMsgEdit.getText().toString());
                break;
        }
    }


    private void findViews(){
        connectBtn = (Button)findViewById(R.id.connect_btn);
        disconnectBtn = (Button)findViewById(R.id.disconnect_btn);
        messageTv = (TextView)findViewById(R.id.message_tv);
        sendMsgEdit = (EditText)findViewById(R.id.send_msg_edit);
        sendMsgBtn = (Button)findViewById(R.id.send_msg_btn);
        svMsg=findViewById(R.id.sv_msg);
    }

    private void initViews(){
        service=new WebSocketService();
        connectBtn.setOnClickListener(this);
        disconnectBtn.setOnClickListener(this);
        sendMsgBtn.setOnClickListener(this);
    }

    String msg="";
    @Override
    protected void getMessage(String msg) {
//        messageTv.setText("");
        this.msg=this.msg+"\n"+msg+"\n";
        messageTv.setText(this.msg);
        svMsg.post(new Runnable() {
            @Override
            public void run() {
                svMsg.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
            }
        });
    }

    @Override
    public void onBackPressed() {
        WebSocketService.closeWebsocket(true);
        stopService(websocketServiceIntent);
        super.onBackPressed();
    }
    /**
     * 通过网络接口取
     * @return
     */
    public static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
