package com.example.morethreaduploadfile;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {

    private Button click;

    private TextView resultTv;

    String uploadUrl = "http://yun918.cn/study/public/file_upload.php";

    private static final String TAG = "MainActivity";
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
        queryAllImage(this);

        click = findViewById(R.id.upload_click);
        resultTv = findViewById(R.id.result);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

    }

    private void uploadFile() {

        File file = new File(Utils.getSDPath()+"/55.jpg");

        ExecutorService mExecutor = Executors.newCachedThreadPool();

        DownThread downThread = new DownThread(uploadUrl,file.getAbsolutePath());

        downThread.setUploadListener(new DownThread.IUploadListener() {
            @Override
            public void uploadSuccess(String result) {
                Log.d(TAG, "uploadSuccess: result="+result);
            }
        });
        mExecutor.execute(downThread);

    }


    public ArrayList<FileInfo> queryAllImage(final Context context) {
        if (context == null) { //判断传入的参数的有效性
            return null;
        }
        ArrayList<FileInfo> images = new ArrayList<FileInfo>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            //查询数据库，参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
            cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null ,null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    FileInfo image = new FileInfo();
                    image.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID))); //获取唯一id
                    image.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))); //文件路径
                    image.setFileName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))); //文件名
                    //...   还有很多属性可以设置
                    //可以通过下一行查看属性名，然后在Images.Media.里寻找对应常量名
                    Log.i(TAG, "queryAllImage --- all column name --- " + cursor.getColumnName(cursor.getPosition()));

                    //获取缩略图（如果数据量大的话，会很耗时——需要考虑如何开辟子线程加载）
                    /*
                     * 可以访问android.provider.MediaStore.Images.Thumbnails查询图片缩略图
                     * Thumbnails下的getThumbnail方法可以获得图片缩略图，其中第三个参数类型还可以选择MINI_KIND
                     */
                    Bitmap thumbnail = MediaStore.Images.Thumbnails.getThumbnail(resolver, image.getId(), MediaStore.Images.Thumbnails.MICRO_KIND, null);
                    image.setThumbnail(thumbnail);

                    images.add(image);
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
