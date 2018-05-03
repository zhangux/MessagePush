package com.qdzl.client;

import android.util.Log;

import com.qdzl.MainActivity;
import com.qdzl.service.SocketService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by QDZL on 2017/12/18.
 */
public class ClientManager {
    private static ClientManager manager;
    private SocketChannel socketChannel;
    private SelectorLoop readBell;
    private OnConnectedListener connectedListener;
    private OnReceivedMessageListener messageListener;
    private ByteBuffer temp = ByteBuffer.allocate(1024);

    public void setOnReceivedMessageListener(OnReceivedMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setOnConnectedListener(OnConnectedListener connectedListener) {
        this.connectedListener = connectedListener;
    }

    private ClientManager() {
    }

    public static ClientManager getInstance() {
        if (manager == null) {
            manager = new ClientManager();
        }
        return manager;
    }

    public void sendMessage(String msg, String charset) {
        sendMessage(msg.getBytes(Charset.forName(charset)));
    }

    public void sendMessage(String msg) {
        Log.e("aaa",msg+"--sendMessage---");
        sendMessage(msg.getBytes(Charset.forName("UTF-8")));
    }

    public void sendMessage(final byte[] msg) {
        new Thread() {
            @Override
            public void run() {
                try {
                    if(socketChannel.isConnected()) {
                        socketChannel.write(ByteBuffer.wrap(msg));
                    }else{
                        Log.e("aaaa","-------连接已断开------");
                        MainActivity.isCon=false;
                        SocketService.startService("192.168.3.11", "9090");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void starts(final String host, final int port) {
        new Thread() {
            @Override
            public void run() {
                try {
                    readBell = new SelectorLoop();
                    // 连接远程server
                    socketChannel = SocketChannel.open();
                    boolean isConnected=false;
                    while (true) {
                        try {
                            isConnected = socketChannel.connect(new InetSocketAddress(host, port));
                            if(isConnected){
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Thread.sleep(10000);
                            starts(host,port);
                            break;
                        }
                    }
                    socketChannel.configureBlocking(false);
                    SelectionKey key = socketChannel.register(readBell.getSelector(), SelectionKey.OP_READ);
                    if (isConnected) {
                        if (connectedListener != null) {
                            connectedListener.onConnected();
                        }
                    } else {
                        key.interestOps(SelectionKey.OP_CONNECT);
                    }
                    new Thread(readBell).start();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    class SelectorLoop implements Runnable {
        private Selector selector;
        private boolean stop;

        public SelectorLoop() throws IOException {
            this.selector = Selector.open();
        }

        public Selector getSelector() {
            return this.selector;
        }

        public void stop() throws IOException {
            this.stop = true;
            if (this.selector.isOpen()) {
                this.selector.close();
            }
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    if (this.selector.select() > 0) {
                        Log.e("aaaa", "--------有数据读取------");
                        Set<SelectionKey> keys = this.selector.selectedKeys();
                        Iterator<SelectionKey> it = keys.iterator();
                        while (it.hasNext()) {
                            SelectionKey key = it.next();
                            it.remove();
                            dispatchSelectionKey(key);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void dispatchSelectionKey(SelectionKey key) throws IOException {
        if (key.isConnectable()) {
            // socket connected
            SocketChannel sc = (SocketChannel) key.channel();
            // 判断此通道上是否正在进行连接操作。
            // 完成套接字通道的连接过程。
            boolean f = sc.isConnectionPending();
            if (f) {
                boolean d = sc.finishConnect();
                Log.e("aaaa", "=====finishConnect=====" + d);
                if (d && connectedListener != null) {
                    connectedListener.onConnected();
                }
            } else {
                Log.e("aaaa", "连接已断开");
                stop();
            }
        } else if (key.isReadable()) {
            // msg received.
            SocketChannel sc = (SocketChannel) key.channel();
            int count = sc.read(temp);
            if (count < 0) {//连接断开
                stop();
                return;
            }
            // 切换buffer到读状态,内部指针归位.
            temp.flip();
            String msg = Charset.forName("UTF-8").decode(temp).toString();
            if (messageListener != null) {
                messageListener.onReceivedMessage(msg);
            }
            // 清空buffer
            temp.clear();
        }
    }

    public void stop() throws IOException {
        if (connectedListener != null) {
            connectedListener.onDisconnected();
        }
        readBell.stop();
        if (socketChannel != null)
            socketChannel.close();
    }

    public interface OnConnectedListener {

        void onConnected();

        void onDisconnected();
    }

    /**
     * 监听客户端发送来的消息
     *
     * @author zz
     */
    public interface OnReceivedMessageListener {
        /**
         * 接收到消息
         *
         * @param msg 客户端信息
         */
        void onReceivedMessage(String msg);
    }
}
