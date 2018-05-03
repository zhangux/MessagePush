package com.qdzl.network;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by xjm on 2016/7/28.
 */
public class HttpUtils {
    private static OkHttpClient client = new OkHttpClient();

    public static Call get(String url, Callback callback) {
        return post(url, null, callback);
    }

    public static Call post(String url, Map<String, String> map, Callback callback) {
        Log.e("aaaa", "HttpUtils---url---" + url);
        //将map封装成RequestBody
        RequestBody body = getFormBody(map);
        //创建请求构建器
        Request.Builder builder = new Request.Builder()
                .url(url);
        //如果存在body则添加post
        if (body != null)
            builder.post(body);
        if (JsonCallback.jSession != null) {
//            url+="&"+JsonCallback.jSession;
            builder.addHeader("cookie",JsonCallback.jSession);
            Log.e("aaaa","----"+JsonCallback.jSession);
        }
        //创建请求
        Call call = client.newCall(builder.build());
        //执行请求
        call.enqueue(callback);
        return call;
    }

    private static FormBody getFormBody(Map<String, String> map) {

        if (map != null && !map.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            Set<Map.Entry<String, String>> set = map.entrySet();
            for (Map.Entry<String, String> entry : set) {
                try {
                    builder.add(entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return builder.build();
        }
        return null;
    }

//    public static void login(final Activity activity, final String phone,
//                             final String pwd, final int identity) {
//        Map<String, String> param = new HashMap<>();
//        param.put(Constants.Param.PHONE, phone);
//        param.put(Constants.Param.PASSWORD, pwd);
//        param.put(Constants.Param.TYPE, String.valueOf(identity));
//        HttpUtils.post(Constants.Url.getUrl(Constants.Url.LOGIN), param,
//                new JsonCallback<Result<User>>() {
//                    @Override
//                    public void onSuccess(Call call, Result<User> data) {
//                        if (data.error.equals(Result.ERRORNO.NO_ERROR)) {
//                            ((App) activity.getApplication()).u = data.data;
//                            activity.sendBroadcast(new Intent(Constants.Intent.UPDATE_LOGIN_STATE));
////                            SharedUtil.saveUserAccount(activity, phone, pwd,identity);
//                            if (activity instanceof LoginActivity) {
//                                ((LoginActivity) activity).tvLoginText.setVisibility(View.INVISIBLE);
//                                activity.finish();
//                            }
//                        } else {
//                            ((LoginActivity) activity).tvLoginText.setVisibility(View.INVISIBLE);
//                            if (!(activity instanceof LoginActivity)) {
//                                LoginActivity.startActivity(activity, 0);
//                            }
//                            ToastUtil.show(data.error.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call call, int code) {
//                        super.onFailure(call, code);
//                        ((LoginActivity) activity).tvLoginText.setVisibility(View.INVISIBLE);
//                    }
//                });
//    }
}
