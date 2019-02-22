package com.example.day15;

import android.Manifest;
import android.content.Context;
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
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Banner mBanner;
    /**
     * 上传文件-进度值
     */
    private Button mUploadBtn;
    String jsonUrl = "http://www.wanandroid.com/banner/json";

    List<String> imgList = new ArrayList<>();
    private TextView progressTv;
    private Button mDownClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

        initView();

        initData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressEvent(ProgressEvent progressEvent) {
        Log.d(TAG, "onProgressEvent:= " + progressEvent.position + "%");
        progressTv.setText(progressEvent.position);

    }

    /**
     * Subscribe表明  能够接收eventbus的事件--订阅接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJSONEvent(JSONEvent jsonEvent) {


        WanBean wanBean = new Gson().fromJson(jsonEvent.result, WanBean.class);

        for (WanBean.DataBean dataBean : wanBean.getData()) {
            imgList.add(dataBean.getImagePath());
        }

        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {

                Glide.with(MainActivity.this).load(path + "").into(imageView);
            }
        });

        mBanner.setImages(imgList);
        mBanner.start();

        Log.d(TAG, "onJSONEvent: " + jsonEvent.result);

    }

    private static final String TAG = "MainActivity";

    private void initData() {
        // banner
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(jsonUrl)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: e=" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);


                JSONEvent jsonEvent = new JSONEvent();
                jsonEvent.result = result;

                EventBus.getDefault().post(jsonEvent);


            }
        });

    }

    private void initView() {
        mBanner = (Banner) findViewById(R.id.banner);
        mUploadBtn = (Button) findViewById(R.id.click);
        mDownClick = (Button) findViewById(R.id.downClick);
        progressTv = (TextView) findViewById(R.id.progress);
        mUploadBtn.setOnClickListener(this);
        mDownClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:
                uploadFile();
                break;
            case R.id.downClick:
                downLoadFile();
                break;
        }
    }

    String url = "http://yun918.cn/study/public/file_upload.php";
    String imgUrl = "http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png";

    private void uploadFile() {
        // 1.获取文件
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "aa.jpg";
        final File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "exists: " + true);
        }

        // file  --reqeustbody
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() { // 媒体 类型 image/jpg
                return MediaType.parse("image/jpg");
            }

            @Override
            public long contentLength() throws IOException { // 上传文件的长度
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException { // 从文件里通过流的形式写入到sink
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long filelength = contentLength();
                    long isReadSize = 0;// 每次读出的大小

                    long readCount = 0;// 计算已经写入的长度
                    while ((isReadSize = source.read(buf, 1024)) != -1) {
                        sink.write(buf, isReadSize);

                        // 进度值
                        readCount += isReadSize;// 计算已经写入的长度

                        int progress = (int) ((100 * readCount) / filelength);
                        Log.d(TAG, "writeTo: ==" + progress);

                        ProgressEvent progressEvent = new ProgressEvent();
                        progressEvent.position = progress + "";

                        EventBus.getDefault().post(progressEvent);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), requestBody)
                .addFormDataPart("key", "hhh")
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .post(multipartBody)
                .build();

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

    private void downLoadFile() {


        String filePath = Environment.getExternalStorageDirectory() + File.separator + "download.jpg";
        final File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(imgUrl)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(TAG, "onFailure: e="+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //IO InputStream outputStream
                InputStream inputStream = response.body().byteStream();


                doStream(inputStream, file.getAbsolutePath());


            }
        });

    }

    /**
     * @param is       输入流  服务器
     * @param filePath 本地文件路径
     */
    public void doStream(InputStream is, String filePath)  {

        byte[] buf = new byte[2048];
        int len = 0;// 每次读取的长度
        FileOutputStream fos = null;
        File file = new File(filePath);
        try {
            long total = file.length();
            fos = new FileOutputStream(file);
            int sum = 0;
            int progress = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);// 写入 读取的长度
                sum += len;
                progress = (int) (sum * 100 / total);

                ProgressEvent progressEvent = new ProgressEvent();
                progressEvent.position = progress + "";
                EventBus.getDefault().post(progressEvent);
                Log.d(TAG, "progress:= " + progress + "%");
            }
            // 下载完成
            ProgressEvent progressEvent = new ProgressEvent();
            if (progress == 100) {
                progressEvent.position = "下载完成";
            }
            EventBus.getDefault().post(progressEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                fos.close();
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
