package com.jiyun_z5;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import  com.jiyun_z5.dao.DaoMaster;
import com.jiyun_z5.dao.DaoSession;

public class JiyunApplication extends Application {

   private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        initDbaseDao(this);
    }

    public void initDbaseDao(Context context) {

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "jiyun.db");

        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();

    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
