package com.leadpcom.light_push;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.leadpcom.light_push.util.FileUtils;
import com.leadpcom.light_push.util.HttpDownloader;

import java.io.File;

/**
 * Created by QDZL on 2017/12/27.
 */

public class TestActivity extends Activity implements View.OnClickListener {
    private Button btImg;
    private Button btVideo;
    private ImageView imgDis;
    private VideoView videoDis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btImg = findViewById(R.id.bt_img);
        btVideo = findViewById(R.id.bt_video);
        imgDis = findViewById(R.id.img_dis);
        videoDis = findViewById(R.id.video_dis);

        btImg.setOnClickListener(this);
        btVideo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_img:
                Log.e("aaaa","-----------bt_img----------");
                new downloadFileThread().start();
                break;
            case R.id.bt_video:
                Log.e("aaaa","-----------bt_video----------");
                new downloadMP3Thread().start();
                break;
        }
    }

    class downloadFileThread extends Thread {
        public void run() {
            HttpDownloader httpDownloader = new HttpDownloader();
            String fileData = httpDownloader.downloadFiles("http://mystudy.bj.bcebos.com/AndroidDemo_009.xml");
            System.out.println(fileData);
        }
    }

    class downloadMP3Thread extends Thread {
        public void run() {
            HttpDownloader httpDownloader = new HttpDownloader();
            int downloadResult = httpDownloader.downloadFiles(
                    "http://192.168.3.11:8080/upload/a.png", FileUtils.imgDir, "a.png");
            Log.e("aaaa", "下载结果：" + downloadResult);
            if(downloadResult==0){
                imgDis.setImageURI(Uri.fromFile(new File(FileUtils.SDCardRoot+FileUtils.imgDir+"a.png")));
            }
        }
    }

}
