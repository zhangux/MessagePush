package com.leadpcom.light_push_frame.entity;

/**
 * Created by yjh on 2017/3/17.
 */

public class Config {
    private String serverip;
    private String socketport;
    private String serverPort;
    private String lightid;
    private String projectname;
    private String mac;

    public String getSocketport() {
        return socketport;
    }

    public void setSocketport(String socketport) {
        this.socketport = socketport;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    public String getLightid() {
        return lightid;
    }

    public void setLightid(String lightid) {
        this.lightid = lightid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Config() {
    }

    public Config(String serverip, String socketport, String serverPort, String lightid, String projectname, String mac) {
        this.serverip = serverip;
        this.socketport = socketport;
        this.serverPort = serverPort;
        this.lightid = lightid;
        this.projectname = projectname;
        this.mac = mac;
    }
}
