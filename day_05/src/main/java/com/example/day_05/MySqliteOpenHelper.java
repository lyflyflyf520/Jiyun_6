package com.example.day_05;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySqliteOpenHelper extends SQLiteOpenHelper {


    public MySqliteOpenHelper(  Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建表

        sqLiteDatabase.execSQL("create table user(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name varchar(20), age TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // 升级数据库
    }


}
