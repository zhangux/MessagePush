package com.qdzl.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.qdzl.R;
import com.qdzl.utils.L;
import com.qdzl.utils.ToastUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by xjm on 2016/7/28.
 */
public abstract class JsonCallback<T> implements Callback {
    private Type type;
    private final int SUCCESS = 0;
    private final int FAILURE = 1;

    public static String jSession;

    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonCallback() {
        //通过反射获取泛型的类型
        Type tmp = getClass().getGenericSuperclass();
        if (!(tmp instanceof ParameterizedType)) {
            throw new ExceptionInInitializerError("can't get type");
        }
        type = ((ParameterizedType) tmp).getActualTypeArguments()[0];
    }

    @Override
    public void onFailure(Call call, IOException e) {
        L.e("network connect error===========", e);
        handler.post(new MsgRunnable(FAILURE, call, 0));
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        int code = response.code();
        try {
            L.i("===========response code:" + code);
            if (code == 200) {
                String json = response.body().string();
                L.e("=========json:" + json);
                T t = JSON.parseObject(json, type);
                try {
                    if(jSession==null){
                        Headers headers=response.headers();
                        Log.e("aaaa","----headers"+headers);
                        List<String> cookies = headers.values("Set-Cookie");
                        String session = cookies.get(0);
                        jSession = session.substring(0, session.indexOf(";"));
                        Log.e("aaaa","----jSession："+jSession);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(new MsgRunnable(SUCCESS, call, t));
            } else {
                handler.post(new MsgRunnable(FAILURE, call, code));
            }
        } catch (IOException e) {
            L.e(e.getMessage(), e);
            handler.post(new MsgRunnable(FAILURE, call, code));
        }
    }

    public void onFailure(Call call, int code) {
        if (code == 0) {
            ToastUtil.show(R.string.network_address_error);
        } else if (code == 404) {
            ToastUtil.show(R.string.network_address_error);
        } else if (code == 500) {
            ToastUtil.show(R.string.server_error);
        } else {
            ToastUtil.show(R.string.network_connect_error);
        }
    }


    public abstract void onSuccess(Call call, T data);

    class MsgRunnable implements Runnable {
        private int what;
        private Call call;
        private T data;
        private int code;

        public MsgRunnable(int what, Call call, T data) {
            this.what = what;
            this.call = call;
            this.data = data;
        }

        public MsgRunnable(int what, Call call, int code) {
            this.what = what;
            this.call = call;
            this.code = code;
        }

        @Override
        public void run() {
            switch (what) {
                case SUCCESS:
                    onSuccess(call, data);
                    break;
                case FAILURE:
                   /* switch (code) {
                        case 0:
                            ToastUtil.show(R.string.network_connect_error);
                            break;
                        case 404:
                            ToastUtil.show(R.string.network_address_error);
                            break;
                        case 500:
                            ToastUtil.show(R.string.server_error);
                            break;
                    }*/
                    onFailure(call, code);
                    break;
            }
        }
    }
}
