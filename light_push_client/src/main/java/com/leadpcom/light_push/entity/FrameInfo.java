package com.leadpcom.light_push.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "FrameInfo")
public class FrameInfo extends Model implements Serializable {
    @Column(name = "fid")
    private Integer fid;// 画屏id
    @Column(name = "ip")
    private String ip;// ip地址
    @Column(name = "lightid")
    private String lightid;// 灯号
    @Column(name = "state")
    private int state;// 在线状态 0断开1连接
    @Column(name = "mid")
    private int mid;// 图片id
    @Column(name = "fileurl")
    private String fileurl;// 图片路径
    @Column(name = "time")
    private double time;// 显示时间
    @Column(name = "startpage")
    private int startpage;// 是否是第一个显示 0否1是
    @Column(name = "span")
    private int span;// 一个画屏对应的图片数量
    @Column(name = "reserve")
    private int reserve;//是否预留图片


    public FrameInfo(String fileurl, int reserve, double time) {
        this.fileurl = fileurl;
        this.reserve = reserve;
        this.time = time;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLightid() {
        return lightid;
    }

    public void setLightid(String lightid) {
        this.lightid = lightid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String imgurl) {
        this.fileurl = imgurl;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getStartpage() {
        return startpage;
    }

    public void setStartpage(int startpage) {
        this.startpage = startpage;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public FrameInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public FrameInfo(Integer fid, String ip, String lightid, int state,
                     int mid, String fileurl, int time, int startpage, int span) {
        super();
        this.fid = fid;
        this.ip = ip;
        this.lightid = lightid;
        this.state = state;
        this.mid = mid;
        this.fileurl = fileurl;
        this.time = time;
        this.startpage = startpage;
        this.span = span;
    }

}
