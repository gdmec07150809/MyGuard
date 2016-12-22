package com.example.administrator.myguard.m7processmanager.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by hhmm on 2016/12/19.
 */
public class AppEntity {
    private Drawable appIcon;
    private String appName;
    private String packageName;
    private double memorySize;

    public Drawable getAppIcon(){
        return appIcon;
    }
    public String getAppName(){
        return appName;
    }
    public void setAppName(String appName){
        this.packageName=packageName;
    }
    public double getMemorySize(){
        return memorySize;
    }
    public void setMemorySize(double memorySize){
        this.memorySize=memorySize;
    }
}

