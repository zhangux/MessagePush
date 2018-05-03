package com.qdzl.socket;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.qdzl.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class SocketManager {
    private ServerSocket server;
    private Handler handler = null;

    public SocketManager(Handler handler) {
        this.handler = handler;
        int port = 9999;
        while (port > 9000) {
            try {
                server = new ServerSocket(port);
                MainActivity.myHost=port+"";
                break;
            } catch (Exception e) {
                port--;
            }
        }
        SendMessage(1, port);
        Thread receiveFileThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ReceiveFile();
                }
            }
        });
        receiveFileThread.start();
    }

    void SendMessage(int what, Object obj) {
        if (handler != null) {
            Message.obtain(handler, what, obj).sendToTarget();
        }
    }

    void ReceiveFile() {
        try {

            Socket name = server.accept();
            InputStream nameStream = name.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(nameStream);
            BufferedReader br = new BufferedReader(streamReader);
            String type = null;
            try {
                type = br.readLine();
                if (type.equals("1")) {
                    String msg=br.readLine();
                    SendMessage(3, msg);
                    SendMessage(0, "正在接收:" + msg);
                } else {
//                    String fileName = br.readLine();
                    br.close();
                    streamReader.close();
                    nameStream.close();
                    name.close();
                    SendMessage(0, "正在接收:" + type);

                    Socket data = server.accept();
                    InputStream dataStream = data.getInputStream();
                    String savePath = Environment.getExternalStorageDirectory().getPath() + "/" + type;
                    FileOutputStream file = new FileOutputStream(savePath, false);
                    byte[] buffer = new byte[1024];
                    int size = -1;
                    while ((size = dataStream.read(buffer)) != -1) {
                        file.write(buffer, 0, size);
                    }
                    file.close();
                    dataStream.close();
                    data.close();
                    SendMessage(0, type + "接收完成");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            SendMessage(0, "接收错误:\n" + e.getMessage());
        }
    }

    public void SendFile(ArrayList<String> fileName, ArrayList<String> path, String ipAddress, int port) {
        try {
            for (int i = 0; i < fileName.size(); i++) {
                Socket name = new Socket(ipAddress, port);
                OutputStream outputName = name.getOutputStream();
                OutputStreamWriter outputWriter = new OutputStreamWriter(outputName);
                BufferedWriter bwName = new BufferedWriter(outputWriter);
                bwName.write("0");
                bwName.write(fileName.get(i));
                bwName.close();
                outputWriter.close();
                outputName.close();
                name.close();
                SendMessage(0, "正在发送" + fileName.get(i));

                Socket data = new Socket(ipAddress, port);
                OutputStream outputData = data.getOutputStream();
                FileInputStream fileInput = new FileInputStream(path.get(i));
                int size = -1;
                byte[] buffer = new byte[1024];
                while ((size = fileInput.read(buffer, 0, 1024)) != -1) {
                    outputData.write(buffer, 0, size);
                }
                outputData.close();
                fileInput.close();
                data.close();
                SendMessage(0, fileName.get(i) + "  发送完成");
            }
            SendMessage(0, "所有文件发送完成");
        } catch (Exception e) {
            SendMessage(0, "发送错误:\n" + e.getMessage());
        }
    }

    public void sendMessage(String type, String msg, String ipAddress, int port) {
        try {
            Socket name = new Socket(ipAddress, port);
            OutputStream outputName = name.getOutputStream();
            OutputStreamWriter outputWriter = new OutputStreamWriter(outputName);
            BufferedWriter bwName = new BufferedWriter(outputWriter);
            bwName.write(type + "\n");
            bwName.write(msg);
            bwName.close();
            outputWriter.close();
            outputName.close();
            name.close();
            SendMessage(0, "正在发送" + msg);
        } catch (Exception e) {
            e.printStackTrace();
            SendMessage(0, "发送错误:\n" + e.getMessage());
        }
    }
    private byte[] getBytes(String msg) throws UnsupportedEncodingException {
        return msg.getBytes(Charset.forName("UTF-8"));
    }
}
