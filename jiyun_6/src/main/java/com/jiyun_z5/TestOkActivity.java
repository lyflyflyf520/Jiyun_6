package com.jiyun_z5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.jiyun_z5.utils.Constant.food_url;

public class TestOkActivity extends AppCompatActivity {

    TextView resultTv;

    private static final String TAG = "TestOkActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ok);

        resultTv = (TextView) findViewById(R.id.result);
        initDataByOk();
    }

    private void initDataByOk() {


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(food_url).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(TAG, "onFailure: e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final  String  result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        resultTv.setText(result);

                    }
                });

                Log.d(TAG, "onResponse: result="+result);
            }
        });

    }
}
