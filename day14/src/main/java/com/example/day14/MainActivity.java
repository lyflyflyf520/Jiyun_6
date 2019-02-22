package com.example.day14;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.WRITE_SECURE_SETTINGS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mClick;
    private TextView mResult;
    /**
     * apk安装
     */
    private Button mClick2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    WRITE_SECURE_SETTINGS};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);
        mClick2 = (Button) findViewById(R.id.click2);
        mClick2.setOnClickListener(this);
    }

    //UnknowApp-1.0.apk
    private String apk_url = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:

                down();
                break;
            case R.id.click2:
                installApk(Environment.getExternalStorageDirectory() + File.separator+"UnknowApp-1.0.apk");
                break;
        }
    }

    private void installApk(String apkPath){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            File file = new File(apkPath);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(this, "com.example.day14", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive");
        }
        startActivity(intent);

    }

    private void down() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {

                    DownLoadUtils downLoadUtils = new DownLoadUtils();

                    downLoadUtils.startDownFile(MainActivity.this, apk_url, null, 3, "UnknowApp-1.0.apk");
//                    Context context, String sourcePath,  String targetFilePath, int threadNumber,   String fileName

//                    Context context, String sourcePath,int threadNumber,
//                    String targetFilePath, String fileName
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
