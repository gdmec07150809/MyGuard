package com.example.administrator.myguard.m7processmanager.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by hhmm on 2016/12/19.
 */
public class SystemInfoUtils {
    public static boolean isServiceRunning(Context context,String className){
        ActivityManager am=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo>infos=am.getRunningServices(200);
        for (ActivityManager.RunningServiceInfo info:infos){
            String serviceClassName=info.service.getClassName();
            if (className.equals(serviceClassName)){
                return true;
            }
        }
        return false;
    }
    public static int getRunningProcessCount(Context context) {
        ActivityManager am=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo>runningAppProcessInfos=am.getRunningAppProcesses();
        int count=runningAppProcessInfos.size();
        return count;
    }

    public static long getAvailMem(Context context) {
        ActivityManager am=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo=new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        long availMem=outInfo.availMem;
        return availMem;
    }

    public static long getTotalMem()  {
        try {
            FileInputStream fis=new FileInputStream(new File("/proc/meninfo"));
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            String totalInfo=br.readLine();
            StringBuffer sb=new StringBuffer();
            for (char c:totalInfo.toCharArray()){
                if (c>='0'&&c<'9'){
                    sb.append(c);
                }
            }
            long bytesize=Long.parseLong(sb.toString())*1024;
            return bytesize;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
