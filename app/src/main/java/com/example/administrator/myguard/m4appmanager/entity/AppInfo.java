package com.example.administrator.myguard.m4appmanager.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by admin on 12/20 0020.
 */
public class AppInfo {
    public boolean isSelected=false;
    public boolean isUserApp;
    public String appName;
    public String packageName;
    public String apkPath;
    public Drawable icon;
    public long appSize;
    public boolean isInRoom;
    public String getAppLocation(boolean isInRoom){
        if (isInRoom){
            return "手机内存";
        }else {
            return "外部存储";
        }
    }
}
