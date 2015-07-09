package com.sarltokyo.pull_load_example3.application;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by osabe on 15/06/30.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
