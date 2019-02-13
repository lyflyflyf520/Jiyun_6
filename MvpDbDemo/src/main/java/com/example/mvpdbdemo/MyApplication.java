package com.example.mvpdbdemo;

import android.app.Application;

import com.example.mvpdbdemo.dao.DaoMaster;
import com.example.mvpdbdemo.dao.DaoSession;

/**
 * 这是应用程序启动入口
 */
public class MyApplication extends Application {
    static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        initDb();

    }

    private void initDb() {

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "jiyun.db");

        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());

        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {

        return daoSession;
    }
}
