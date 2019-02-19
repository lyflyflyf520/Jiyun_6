package com.example.day13_upload_video;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    String uploadUrl = "http://yun918.cn/study/public/file_upload.php";
    /**
     * 上传视频
     */
    private Button mClick;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        EventBus.getDefault().register(this);

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

    private void initData() {

//        1.获取file
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "xx.mp4";
        File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "exists: " + true);
        }
//        2. file--> RequestBody
        final RequestBody requestBody = RequestBody.create(MediaType.parse("video/mp4"), file);

//        3.MultiPart

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "video")// 建立服务器的文件夹
                .addFormDataPart("uid", "728")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
//4.创建一个request对象
        final Request request = new Request.Builder()
                .url(uploadUrl)
                .post(multipartBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: error="+e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String result  = response.body().string();

                MsgEvent msg = new MsgEvent();
                msg.setResult(result);

                EventBus.getDefault().post(msg);

            }
        });

    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:
                initData();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent msgEvent){

        mResult.setText(msgEvent.getResult());
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
