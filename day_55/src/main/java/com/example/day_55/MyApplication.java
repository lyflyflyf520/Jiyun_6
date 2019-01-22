package com.example.day_55;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {

    static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();


        Stetho.initializeWithDefaults(this);
        initDbDao();

    }
    private void initDbDao(){
        // 创建操作数据库的对象
        DaoMaster.DevOpenHelper devOpenHelper =  new DaoMaster.DevOpenHelper(this,"food.db");
         DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession=  daoMaster.newSession();

    }

    public static DaoSession getDaosession(){
        return  daoSession;
    }

}
