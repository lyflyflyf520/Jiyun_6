package com.example.mvp_34;

import android.app.Application;

public class MyApplication extends Application {
    public static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        initDb();

    }

    private void initDb(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this,"food.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
         daoSession = daoMaster.newSession();

    }
    public static DaoSession getDaosession(){

        return daoSession;
    }
}
