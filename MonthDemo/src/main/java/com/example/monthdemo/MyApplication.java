package com.example.monthdemo;

import android.app.Application;

import com.example.monthdemo.dao.DaoMaster;
import com.example.monthdemo.dao.DaoSession;

public class MyApplication extends Application {

    static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        initDao();

    }

    private void initDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "juyun6.db");

        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());

        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaosession() {

        return daoSession;
    }
}
