package com.example.blockcanary;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}
