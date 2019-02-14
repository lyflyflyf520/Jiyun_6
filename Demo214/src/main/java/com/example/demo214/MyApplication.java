package com.example.demo214;

import android.app.Application;

import com.example.demo214.dao.DaoMaster;
import com.example.demo214.dao.DaoSession;

public class MyApplication extends Application {
    static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        initDb();
    }

    public void initDb() {

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "news.db");

        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
         daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){

        return daoSession;
    }
}
