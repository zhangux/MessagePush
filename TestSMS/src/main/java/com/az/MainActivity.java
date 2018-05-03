package com.az;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.az.utils.Toast;

public class MainActivity extends Activity {
    private Button btStartServer;
    private Button btStopServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind();
        init();
    }

    private void bind() {
        btStartServer = findViewById(R.id.bt_startServer);
        btStopServer = findViewById(R.id.bt_stopServer);
    }

    private void init() {
        btStartServer.setOnClickListener(btOnClick);
        btStopServer.setOnClickListener(btOnClick);
    }
    public static void sendMessage(Integer what,String msg){
        Message message=new Message();
        message.what=what;
        message.obj=msg;
        myHandler.sendMessage(message);
    }
    static Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            switch (msg.what){
//
//            }
            Toast.show(msg.obj.toString());
        }
    };

    View.OnClickListener btOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_startServer:
                    break;
                case R.id.bt_stopServer:
                    break;
            }
        }
    };
}
