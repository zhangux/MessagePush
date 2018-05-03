package com.leadpcom.light_push.util;

import android.content.Context;
import android.database.Cursor;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.leadpcom.light_push.entity.FrameInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by yjh on 2017/3/16.
 */

public class DBUtil {
    //初始化数据库
    public static void initDB(Context context) {
        Configuration configuration = new Configuration.Builder(context)
                .setDatabaseName("light_push")
                .setDatabaseVersion(1)
                .addModelClasses(FrameInfo.class)
                .create();
        ActiveAndroid.initialize(configuration);
        initImages(context);
    }

    //初始化预设图片
    public static void initImages(Context context) {
        String[] names = null;
        try {
            names = context.getAssets().list("pictures");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (names != null && names.length > 0) {
            boolean need = checkUpdateReserveImage(names);
            try {
                if (need) {
                    ActiveAndroid.beginTransaction();
                    //清除所有预设图片
                    clearImageByType(0);
                    for (String name : names) {
                        FrameInfo info = new FrameInfo(name, 0, 0.1);
                        info.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (need)
                    ActiveAndroid.endTransaction();
            }
        }
    }

    //按图片类型清除图片
    public static void clearImageByType(int type) {
        new Delete().from(FrameInfo.class).where("reserve=?", type).execute();
    }

    //检查预设图片是否需要更新
    private static boolean checkUpdateReserveImage(String[] names) {
        StringBuilder sb = new StringBuilder("select count(0) from FrameInfo where reserve=0 and fileurl in (");
        for (int i = 0; i < names.length; i++) {
            sb.append("?,");
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");
        Cursor c = ActiveAndroid.getDatabase().rawQuery(sb.toString(), names);
        if (c != null && c.moveToFirst()) {
            int count = c.getInt(0);
            if (count != names.length) return true;
        }
        return false;
    }


    public static List<FrameInfo> loadFrameInfos() {
        From from = new Select().from(FrameInfo.class).where("reserve=1");
        if (from.exists()) {
            return from.execute();
        }
        return new Select().from(FrameInfo.class).where("reserve=0").execute();
    }

    public static void updateFrameOrder(int mid) {
        try {
            ActiveAndroid.beginTransaction();//开启事务
            new Update(FrameInfo.class).set("startpage=0").where("startpage=1").execute();
            new Update(FrameInfo.class).set("startpage=1").where("mid=?", mid).execute();
            ActiveAndroid.setTransactionSuccessful();//设置事务成功
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ActiveAndroid.endTransaction();//结束事务
        }
    }
}
