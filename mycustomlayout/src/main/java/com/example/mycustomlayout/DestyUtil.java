package com.example.mycustomlayout;

import android.content.Context;
import android.util.DisplayMetrics;

public class DestyUtil {

    public static DisplayMetrics getPingMU(Context mContext){
       return mContext.getResources().getDisplayMetrics();
    }
}
