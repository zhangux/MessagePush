package com.leadpcom.light_push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leadpcom.light_push.adapter.ImageAdapter;
import com.leadpcom.light_push.core.App;
import com.leadpcom.light_push.entity.FrameInfo;
import com.leadpcom.light_push.socket.ClientManager;
import com.leadpcom.light_push.socket.SocketService;
import com.leadpcom.light_push.util.DBUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private ImageAdapter imageAdapter;
    private Handler handler = new Handler();
    private AnimationSet as;

    //是否连接成功
    public static boolean isCon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);//
        startAnimation();
        Log.e("aaaa", "-----onCreate------");
        pager = (ViewPager) findViewById(R.id.pager);
        imageAdapter = new ImageAdapter(this, DBUtil.loadFrameInfos());
        pager.setAdapter(imageAdapter);
        pager.addOnPageChangeListener(pageChangeLis);
        setStartPage();
        if (App.getConfig() != null) {
            Toast.makeText(MainActivity.this, App.getConfig().getServerip(), Toast.LENGTH_SHORT).show();
            SocketService.startService(App.getConfig().getServerip(), App.getConfig().getSocketport());
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void event(List<FrameInfo> infos) {
        if (infos != null && infos.size() > 0) {
            handler.removeCallbacks(nextPage);
            imageAdapter.setData(infos);
            setStartPage();
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(nextPage);
        EventBus.getDefault().unregister(this);
        ClientManager.getInstance().end();
        super.onDestroy();
        System.exit(0);
    }

    private void setStartPage() {
        if (imageAdapter.getData().size() < 2)
            return;
        int index = 0;
        for (FrameInfo info : imageAdapter.getData()) {
            if (info.getStartpage() == 1) {
                index = imageAdapter.getData().indexOf(info);
                break;
            }
        }
        pager.setCurrentItem(index, false);//不平滑滚动
        handleNextPage(index);
    }

    private void handleNextPage(int position) {
        if (position < 0)
            position = imageAdapter.getData().size() - 1;
        if (position >= imageAdapter.getData().size())
            position = 0;
        Log.e("aaa", "position=======" + position);
        // Log.e("aaa","mid======="+imageAdapter.getData().get(position).getMid());
        long time = (long) (imageAdapter.getData().get(position).getTime() * 60 * 1000);
        // Log.e("aaa", "time=====" + time);
        //Toast.makeText(MainActivity.this, "---time---" + time, Toast.LENGTH_LONG).show();
        handler.removeCallbacks(nextPage);
        handler.postDelayed(nextPage, time);//分钟为单位
    }

    private Runnable nextPage = new Runnable() {
        @Override
        public void run() {
            int curr = pager.getCurrentItem();
            if (curr + 1 < imageAdapter.getCount()) {
                pager.setCurrentItem(curr + 1);//默认true平滑滚动
            } else {
                pager.setCurrentItem(0);
            }
        }
    };


    private ViewPager.OnPageChangeListener pageChangeLis = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.i("aaa", "-------- onPageScrolled-------");
        }

        @Override
        public void onPageSelected(int position) {
            int count = pager.getChildCount();
            int dataCount = imageAdapter.getCount();
            View view;
//            if (dataCount == 2) {
//                view = pager.getChildAt(1 - position);
//            } else if (dataCount == 3) {
//                if (count == 2) {
//                    view = pager.getChildAt(1);
//                } else if (position == 0) {
//                    view = pager.getChildAt(2);
//                } else {
//                    if (pagerFirst) {
//                        view = pager.getChildAt(0);
//                    } else {
//                        pagerFirst = true;
//                        view = pager.getChildAt(1);
//                    }
//                }
//            } else {
//                if (count == 4) {
//                    view = pager.getChildAt(2);
//                } else {
//                    view = pager.getChildAt(1);
//                }
//            }
            view = pager.getChildAt(position);
            //添加动画
            // if (view != null) view.startAnimation(as);
            int mid = imageAdapter.getData().get(position).getMid();
            //Toast.makeText(MainActivity.this, mid + "   ", Toast.LENGTH_SHORT).show();
            if (mid > 0) {
                String lightid = imageAdapter.getData().get(position).getLightid();
                int fid = imageAdapter.getData().get(position).getFid();
                //把当前所播放的图片id保存在本地数据库
                DBUtil.updateFrameOrder(mid);
                //把当前所播放的图片id保存在服务器数据库
                JSONObject jo = new JSONObject();
                jo.put("act", 2);//提交正在播放图片id
                JSONObject jo2 = new JSONObject();
                jo2.put("mid", mid);
                jo2.put("fid", fid);
                jo2.put("lightid", lightid);
                jo.put("data", jo2);
                String json = jo.toJSONString();
                Log.i("aaa", "--------" + json);
                ClientManager.getInstance().sendMessage(json);
            }
            handleNextPage(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.i("aaa", "-------- onPageScrollStateChanged-------");
        }

    };

    public static void startActivity(Context context) {
        Intent in = new Intent(context, MainActivity.class);
        context.startActivity(in);
    }

    private void startAnimation() {
// false 代表动画集中每种动画都采用各自的动画插入器（数字函数）
        as = new AnimationSet(false);
//旋转动画，锚点
//        RotateAnimation ra = new RotateAnimation(
//                0, 720,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);//设置锚点为图片的中心点
// 设置动画播放时间
//        ra.setDuration(900);
//        ra.setFillAfter(true);//动画播放完之后，停留在当前状态 

// 添加到动画集
//        as.addAnimation(ra);

// 渐变动画
        AlphaAnimation aa = new AlphaAnimation(0, 1);//由完全透明到不透明 
        aa.setDuration(1500);
        aa.setFillAfter(true);
        as.addAnimation(aa);

// 缩放动画
        ScaleAnimation sa = new ScaleAnimation(
                0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(1500);
        sa.setFillAfter(true);
        as.addAnimation(sa);

// 播放动画
        //imageView.startAnimation(as);
        //textView.startAnimation(as);
    }
}
