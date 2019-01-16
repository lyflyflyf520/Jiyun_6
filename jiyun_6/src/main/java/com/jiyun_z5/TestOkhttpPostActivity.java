package com.jiyun_z5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import static com.jiyun_z5.utils.Constant.post_url;

/**
 * 请求头处理（Header）https://kb.cnblogs.com/page/92320/
 * 请求体处理（Form表单，String字符串，流，文件）
 *
 */
public class TestOkhttpPostActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    private String TAG = "getDataByOkhttpPost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_okhttp_post);

        ButterKnife.bind(this);

        getDataByForm();
        getDataByString();
        postDataByStream();
        postDataByFile();
    }

    /**
     * 上传文件
     */
    public void postDataByFile(){
        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        File file = new File("test.md");

        RequestBody requestBody = RequestBody.create(mediaType, file);


        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });

    }

    /**
     * 提交数据通过stream
     */
    public void postDataByStream(){
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("I am Jdqm.");
            }
        };

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });

    }
    /**
     * 通过string发送数据
     */
    public void getDataByString(){
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String str = "I am programer";
        RequestBody requestBody = RequestBody.create(mediaType, str);  // 创建requestbody

        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)  // this is requestBody
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });


    }
    /**
     *   通过表单发送参数
     */
    public void getDataByForm() {
        try {
            //post方式提交的数据
            FormBody formBody = new FormBody.Builder()
                    .add("username", "android")
                    .add("password", "50")
                    .add("phone", "50")
                    .add("verify", "50")
                    .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .post(formBody)
//                    .header("User-Agent", "my-agent")
//                    .addHeader("Accept-Language", "zh-cn")
                    .url(post_url)
                    .build();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    Log.d(TAG, "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String result = response.body().string();


                    Log.d(TAG, "onResponse: " + result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(result);
                        }
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
