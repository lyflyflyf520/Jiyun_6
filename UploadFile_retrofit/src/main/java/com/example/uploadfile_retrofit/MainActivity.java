package com.example.uploadfile_retrofit;

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

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 点击上传
     */
    private Button mClick;
    private TextView mResult;

    private static final String TAG = "MainActivity";
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
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    };
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

    public void uploadFile(){



        // 获取文件
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "aa.jpg";
        File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "exists: " + true);
        }
        // 构建requestBody 对象
        RequestBody  requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);

        // 文件上传Body
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

        // 参数
        RequestBody rbParams =RequestBody.create(MediaType.parse("text/plain"), "hello,retrofit");

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(IUploadService.uploadUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IUploadService iUploadService = retrofit.create(IUploadService.class);

        Call<ResponseBody>  call =  iUploadService.uploadFile(rbParams,multipartBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: "+response.body().string());

                    //    nResponse: {"code":200,"res":"上传文件成功","data":
                    // {"url":"http:\/\/yun918.cn\/study\/public\/uploadfiles\/hello,retrofit\/aa.jpg"}}

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d(TAG, "onFailure: "+t.getMessage());
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
                uploadFile();
                break;
        }
    }
}
