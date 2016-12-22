package com.example.administrator.myguard.m8trafficmonitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.myguard.m7processmanager.utils.SystemInfoUtils;
import com.example.administrator.myguard.m8trafficmonitor.service.TrafficMonitoringService;

/**
 * Created by hhmm on 2016/12/19.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!SystemInfoUtils.isServiceRunning(context,"com.example.administrator.myguard.m8trafficmonitor.receiver.TrafficMonitoringService")){
            Log.d("traffic service","turn on");
            context.startService(new Intent(context, TrafficMonitoringService.class));
        }
    }
}
