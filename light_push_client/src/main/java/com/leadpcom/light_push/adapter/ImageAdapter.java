package com.leadpcom.light_push.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.leadpcom.light_push.R;
import com.leadpcom.light_push.core.App;
import com.leadpcom.light_push.entity.FrameInfo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by yjh on 2017/3/15.
 */

public class ImageAdapter extends PagerAdapter {

    private List<FrameInfo> infos;
    private LayoutInflater lif;
    private Picasso picasso;
    private OkHttpClient okHttpClient;
    private static String host = App.getConfig().getServerip();
    private static final String PATH = "http://" + host + ":" + App.getConfig().getServerPort() + "/";
    private static final String HTTPPATH = PATH + App.getConfig().getProjectname() + "/";
    private static final String ASSETSPATH = "file:///android_asset/pictures/";

    public ImageAdapter(Context context, List<FrameInfo> infos) {
        this.infos = infos;
        lif = LayoutInflater.from(context);
        /**
         * 设置超时时间
         */
//        okHttpClient = new OkHttpClient();
//        okHttpClient.setConnectTimeout(500, TimeUnit.SECONDS);
//        okHttpClient.setReadTimeout(500, TimeUnit.SECONDS);
//        okHttpClient.setWriteTimeout(500, TimeUnit.SECONDS);
//        picasso = new Picasso.Builder(context)
//                .downloader(new OkHttpDownloader(okHttpClient))
//                .build();
        picasso = Picasso.with(context);
    }

    @Override
    public int getCount() {
        return infos != null ? infos.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = lif.inflate(R.layout.layout_page, null);
        ImageView img = (ImageView) view.findViewById(R.id.image);
        VideoView video = (VideoView) view.findViewById(R.id.video);
        //设置推送的图片
        String path = null;
        FrameInfo info = infos.get(position);
        Log.e("aaaa", "----------" + infos.get(position).getFileurl());
        if (infos.get(position).getFileurl().endsWith(".mp4")) {
            Log.e("aaaa", "----------" + "mp4");
            video.setVisibility(View.VISIBLE);
            img.setVisibility(View.GONE);
            video.setVideoPath(PATH + infos.get(position).getFileurl());
            video.start();
        } else {
            video.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
            if (info.getReserve() == 0) {
                path = ASSETSPATH + infos.get(position).getFileurl();
            } else {
                path = HTTPPATH + infos.get(position).getFileurl();
            }
            Log.e("aaaa", "======path=======" + path);
            picasso.load(path)
                    .centerInside()
                    .placeholder(R.mipmap.post_image_loding)
                    .error(R.mipmap.post_image_loading_failed)
                    .resize(App.width, App.height).centerInside()
                    .into(img);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setData(List<FrameInfo> infos) {
        this.infos = infos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public List<FrameInfo> getData() {
        return this.infos;
    }

}
