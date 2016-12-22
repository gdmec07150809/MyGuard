package com.example.administrator.myguard.m4appmanager.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.administrator.myguard.m4appmanager.entity.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12/20 0020.
 */
public class AppInfoParser {

    public static List<AppInfo> getAppInfos(Context context) {
        PackageManager pm=context.getPackageManager();
        List<PackageInfo> packInfos=pm.getInstalledPackages(0);
        List<AppInfo> appinfos=new ArrayList<AppInfo>();
        for (PackageInfo packInfo:packInfos){
            AppInfo appinfo=new AppInfo();
            String packname=packInfo.packageName;
            appinfo.packageName=packname;
            Drawable icon=packInfo.applicationInfo.loadIcon(pm);
            appinfo.icon=icon;
            String appname= packInfo.applicationInfo.loadLabel(pm).toString();
            appinfo.appName=appname;
            String apkpath=packInfo.applicationInfo.sourceDir;
            appinfo.apkPath=apkpath;
            File file=new File(apkpath);
            long appSize=file.length();
            appinfo.appSize=appSize;
            int flags=packInfo.applicationInfo.flags;
            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE&flags)!=0){
                appinfo.isInRoom=false;
            }else {
                appinfo.isInRoom=true;
            }
            if ((ApplicationInfo.FLAG_SYSTEM&flags)!=0){
                appinfo.isUserApp=false;
            }else {
                appinfo.isUserApp=true;
            }
            appinfos.add(appinfo);
            appinfo=null;
        }
        return appinfos;
    }
}
