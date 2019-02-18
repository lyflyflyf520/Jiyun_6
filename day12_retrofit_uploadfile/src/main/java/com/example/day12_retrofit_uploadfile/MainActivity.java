package com.example.day12_retrofit_uploadfile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 上传
     */
    private Button mClick;
    private TextView mResult;
    private ImageView mImg;

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

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);
        mImg = (ImageView) findViewById(R.id.img);
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

    private static final String TAG = "MainActivity";
    private void uploadFile() {

//        1.File
        // 1.获取文件
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "aa.jpg";
        File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "exists: " + true);
        }
//        2 文件类型请求对象
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);

        // 3.文件上传 多媒体对象
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file",file.getName(),requestBody);


//        4.  post普通参数  key (文件夹的名字)  img

        RequestBody postParams =RequestBody.create(MediaType.parse("text/plain"), "img");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UploadService.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UploadService uploadService = retrofit.create(UploadService.class);

        Call call = uploadService.uploadFile(postParams, multipartBody);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = response.body().toString();

                                JSONObject jsonObject = new JSONObject(result);

                                jsonObject = jsonObject.getJSONObject("data");
                                String imgUrl = jsonObject.getString("url");

                                Glide.with(MainActivity.this).load(imgUrl).into(mImg);


                            mResult.setText(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onResponse: ");
                    }
                });

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
