package com.example.morethreadloadfile;

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

/**
 * 多线程下载原理：
 * 把一个文件分为N段，然后创建N个线程
 * 计算每个线程对应下载文件的起点位置和长度
 * 然后去下载每一段的数据。当所有的线程执行完毕，且没有异常，证明下载完毕
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 上传视频
     */
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
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_SECURE_SETTINGS};
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:
                loadFile();
                break;
            case R.id.click2:

                installApk();
                break;
        }
    }

    private void installApk() {

        String targetFilePathAndName = Environment.getExternalStorageDirectory() + File.separator + "UnknowApp-1.0.apk";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(targetFilePathAndName);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(MainActivity.this, "com.example.morethreadloadfile", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(targetFilePathAndName)),
                    "application/vnd.android.package-archive");
        }
        startActivity(intent);

    }

    //UnknowApp-1.0.apk
    private String apk_url = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";

    /**
     * 下载文件的方法
     */
    private void loadFile() {

        try {
            DownLoadUtils downLoadUtils = new DownLoadUtils();
            // 开始下载文件
            downLoadUtils.startMoreThreadDownLoadFile(MainActivity.this,
                    apk_url,
                    null,
                    3,
                    "UnknowApp-1.0.apk");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
