package com.qdzl.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by QDZL on 2018/1/19.
 */

public class MyAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public MyAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//
//                return "消息";
//            case 1:
//
//                return "联系人";
//            case 2:
//
//                return "动态";
//        }
//        return super.getPageTitle(position);
//    }
}