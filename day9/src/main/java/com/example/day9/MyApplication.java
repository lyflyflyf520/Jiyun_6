package com.example.day9;

import android.app.Application;

import com.example.day9.dao.DaoMaster;
import com.example.day9.dao.DaoSession;

public class MyApplication extends Application {

    static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        initDb();

    }

    public void initDb(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this,"food.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());

         daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaosession(){

        return daoSession;
    }
}
