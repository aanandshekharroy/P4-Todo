package com.c3cyberclub.p5_todoapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by theseus on 11/7/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
