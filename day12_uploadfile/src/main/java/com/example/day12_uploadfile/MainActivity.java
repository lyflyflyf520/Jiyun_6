package com.example.day12_uploadfile;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    String url = "http://yun918.cn/study/public/file_upload.php";
    /**
     * 上传
     */
    private Button mClick;
    private TextView mResult;

    private static final String TAG = "MainActivity";
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

    /**
     * uploadfile
     * <p>
     * key
     * file
     */
    private void uploadFile() {

        // 1.获取文件
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "aa.jpg";
        File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "exists: " + true);
        }
        // 2.创建文件上传请求对象
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);

//        3.new okHttp
        OkHttpClient okHttpClient = new OkHttpClient();

        // 4.创建多媒体 请求对象
        RequestBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)  // 表单类型
                .addFormDataPart("key", "img")  // 文件上传的参数
                .addFormDataPart("file", file.getName(), fileBody)
                .build();

        //5. 创建request对象
        final Request request = new Request.Builder()
                .url(url)
                .post(multipartBody)
                .build();
        // 6 获取Call 请求对象
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = response.body().string();

                            try {
                                JSONObject jsonObject = new JSONObject(result);

                                jsonObject = jsonObject.getJSONObject("data");
                                String imgUrl = jsonObject.getString("url");

                                Glide.with(MainActivity.this).load(imgUrl).into(mImg);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mResult.setText(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onResponse: ");
                    }
                });

            }
        });


    }
}
