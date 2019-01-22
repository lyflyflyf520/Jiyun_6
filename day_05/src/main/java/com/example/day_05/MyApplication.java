package com.example.day_05;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {

    static MySqliteOpenHelper mySqliteOpenHelper;
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        initDb();
        initGreendao();
    }

    /**
     * 创建sqliteopenHelper
     * 初始化 操作数据库 类
     */
    public void initDb(){

        mySqliteOpenHelper=  new MySqliteOpenHelper(MyApplication.this,"user.db",null,1);
    }

    /**
     * 给外界 提动获取该对象的方法
     * @return
     */
    public static MySqliteOpenHelper getSqliterHelper(){

       return  mySqliteOpenHelper;
    }


    static DaoMaster daoMaster;

    /**
     * 初始化greendao 对象
     */
    private void initGreendao(){
        // DevOpenHelper
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this,"person.db");
        // daoMaster
         daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
    }

    public static DaoMaster getDaoMaster(){

        return daoMaster;
    }
}
