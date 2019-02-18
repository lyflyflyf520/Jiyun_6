package com.example.morethreaduploadfile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
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


}
