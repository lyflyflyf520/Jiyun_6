package com.example.day5;

import android.app.Application;

import com.example.day5.dao.DaoMaster;
import com.example.day5.dao.DaoSession;
import com.facebook.stetho.Stetho;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }






}
