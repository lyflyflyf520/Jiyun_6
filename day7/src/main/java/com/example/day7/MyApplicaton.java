package com.example.day7;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class MyApplicaton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(getApplicationContext());

    }
}
