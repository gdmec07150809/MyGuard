package com.example.administrator.myguard.m4appmanager.utils;

import android.content.Context;

/**
 * Created by admin on 12/20 0020.
 */
public class DensityUtil {

    public static int dip2px(Context context,float dpValue){
            try {
                final float scale=context.getResources().getDisplayMetrics().density;
                return (int) (dpValue*scale+0.5f);
            }catch (Exception e){
                e.printStackTrace();
            }
        return (int) dpValue;
    }

    public static int px2dip(Context context,float pxValue){
        try{
            final float scale=context.getResources().getDisplayMetrics().density;
            return (int) (pxValue/scale+0.5f);
        }catch (Exception e){
            e.printStackTrace();
        }
        return (int) pxValue;
    }
}
