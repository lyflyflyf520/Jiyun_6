package com.example.uploadfile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

//import okhttp3.Call;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    文件上传接口：  String url = "http://yun918.cn/study/public/file_upload.php";
//    传输类型    post
//    参数：(String  key,String  file);
//    key    上传文件的文件夹（自己随意传）
//    file  固定的"file"参数里面放上传文件的流内容
//

    String url = "http://yun918.cn";
    /**
     * 上传!
     */
    private Button mClick;


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

    }

    private static final String TAG = "MainActivity";

    private void okUploadFile() {


        // 获取文件
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "aa.jpg";
        File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "exists: " + true);
        }
        // 创建文件上传请求对象
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);

        // 构建okhttpclient 对象
        OkHttpClient okHttpClient = new OkHttpClient();

        // 创建多媒体 请求对象
        RequestBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("key","img")  // 文件上传的参数
                .addFormDataPart("file", file.getName(), fileBody)
                .build();

        // 准备 真实的请求对象
        final Request request = new Request.Builder()
                .post(multipartBody)
                .url(url)
                .build();
        // 发送请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(TAG, "onFailure: e=" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d(TAG, "onResponse: response=" + response.body().string());
            }
        });


    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:
                okUploadFile();
                break;

        }
    }

}
