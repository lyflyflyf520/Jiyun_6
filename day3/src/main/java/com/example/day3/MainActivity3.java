package com.example.day3;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.day.service.IRegisterService;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  post 演示
 */
public class MainActivity3 extends Activity {

    Button registerBtn;
    TextView resultTv;
    private static final String TAG = "MainActivity3";
    @Override
    protected void onCreate(  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        registerBtn = findViewById(R.id.regiter_btn);
         resultTv = findViewById(R.id.result);

         registerBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 getPostRetrofit();
             }
         });

    }

    private void getPostRetrofit(){
        // 创建retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yun918.cn/study/public/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRegisterService iRegisterService = retrofit.create(IRegisterService.class);

//        Call<ResponseBody>  call= iRegisterService.getResisterInfo("lisi","890","172789798","1231");
        HashMap<String,String> map = new HashMap<>();
//         （String username,String password,String phone,String verify）
            map.put("username","1212");
            map.put("password","121");
            map.put("phone","121");
            map.put("verify","1212");
        Call<ResponseBody>  call= iRegisterService.getResisterInfo(map);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: ");
                try {
                    final String result = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultTv.setText(result);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
