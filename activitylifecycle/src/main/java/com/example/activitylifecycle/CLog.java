package com.example.activitylifecycle;


import android.util.Log;

public final class CLog {
    public static final boolean DEBUG = true;

    private CLog() {
    }

    public static void d(String tag, String desc) {
        if (DEBUG)
            Log.d(tag, desc);
    }

    public static void d(String tag, String desc, Throwable tr) {
        if (DEBUG)
            Log.d(tag, desc, tr);
    }

    public static void v(String tag, String desc) {
        if (DEBUG)
            Log.v(tag, desc);
    }
    public static void v(String tag, String desc, Throwable tr) {
        if (DEBUG)
            Log.v(tag, desc);
    }

    public static void w(String tag, String desc) {
        if (DEBUG)
            Log.w(tag, desc);
    }

    public static void w(String tag, Throwable ioe) {
        if (DEBUG)
            Log.w(tag, ioe);
    }

    public static void w(String tag, String desc, Throwable e) {
        if (DEBUG)
            Log.w(tag, desc, e);
    }

    public static void i(String tag, String desc) {
        if (DEBUG)
            Log.i(tag, desc);
    }

    public static void i(String tag, String desc, Throwable tr) {
        if (DEBUG)
            Log.i(tag, desc, tr);
    }

    public static void e(String tag, String desc) {
        if (DEBUG)
            Log.e(tag, desc);
    }

    public static void e(String tag, String desc, Throwable tr) {
        if (DEBUG)
            Log.e(tag, desc, tr);
    }
}

