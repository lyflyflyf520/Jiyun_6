package com.example.day13_photo_list;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListview;

    private static final String TAG = "MainActivity";

    ArrayList<FileInfo> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

        String filePath = Environment.getExternalStorageDirectory() + File.separator + "aa.jpg";
        File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "exists: " + true);
        }


        initView();
    }


    private void initView() {
        mListview = (ListView) findViewById(R.id.listview);
        MyAdapter myAdapter = new MyAdapter(this);
        mListview.setAdapter(myAdapter);

        ArrayList<FileInfo> fileInfos = queryAllImage(this);

        myAdapter.updateListData(fileInfos);
    }

    public ArrayList<FileInfo> queryAllImage(final Context context) {
        if (context == null) { //判断传入的参数的有效性
            return null;
        }
        images = new ArrayList<FileInfo>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            //查询数据库，参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
            cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    FileInfo image = new FileInfo();
                    image.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID))); //获取唯一id
                    image.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))); //文件路径
                    image.setFileName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))); //文件名
                    //...   还有很多属性可以设置
                    //可以通过下一行查看属性名，然后在Images.Media.里寻找对应常量名
                    Log.i(TAG, "queryAllImage --- " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));

                    /*
                     * 可以访问android.provider.MediaStore.Images.Thumbnails查询图片缩略图
                     * Thumbnails下的getThumbnail方法可以获得图片缩略图，其中第三个参数类型还可以选择MINI_KIND
                     */
                    Bitmap thumbnail = MediaStore.Images.Thumbnails.getThumbnail(resolver, image.getId(), MediaStore.Images.Thumbnails.MICRO_KIND, null);
                    image.setThumbnail(thumbnail);

                    images.add(image);
                    if (images.size() >= 10) {
                        break;
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return images;
    }

}
