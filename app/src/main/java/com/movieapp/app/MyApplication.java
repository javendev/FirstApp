package com.movieapp.app;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by Javen on 2016/7/11.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Logger .init("Javen");
    }
}