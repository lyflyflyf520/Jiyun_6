package com.example.frescodemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplicatoin extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Fresco.initialize(this);

    }
}
