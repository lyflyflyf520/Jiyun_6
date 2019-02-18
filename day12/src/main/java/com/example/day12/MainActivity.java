package com.example.day12;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.WRITE_SECURE_SETTINGS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String imgUrl = "http://cms-bucket.ws.126.net/2019/02/15/97392c98fe22485a8a2458ba9dd907d9.jpeg";

    /**
     * Hello World!
     */
    private ImageView mImg;


    private DiskCache diskCache;
    private static final String TAG = "MainActivity";
    /**
     * 点击
     */
    private Button mClick;

    ImageCacheUtil imageCacheUtil ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        imageCacheUtil = new ImageCacheUtil(this);

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


    @Override
    protected void onStart() {
        super.onStart();




    }

    private void diskCacheData() {

        Bitmap bitmap = imageCacheUtil.getBitmap(imgUrl);
        if (bitmap != null) {
            mImg.setImageBitmap(bitmap);
        } else {
            requestNetBitmap();
        }

    }

    private void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
    }

    /**
     * 请求网络的图片对象
     */
    private void requestNetBitmap() {


        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(imgUrl)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

//                final byte[] bytes = response.body().bytes();
                final InputStream inputStream = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    // 保存数据

                    imageCacheUtil.saveBitmap(imgUrl,bitmap);

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            mImg.setImageBitmap(bitmap);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


//                Log.d(TAG, "onResponse: bitmap=" + bitmap);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:
                diskCacheData();
                break;
        }
    }
}
