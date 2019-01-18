package com.example.day3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.day.service.IFoodService;
import com.example.day3.bean.Banner;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 实现retrofit 请求数据
 * 通过两种 get post
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getNetDataByGet();
    }

    /**
     * get方式获取网络请求
     *
     * ctrl+R  替换
     *
     */
    private void getNetDataByGet(){

//        构建retrofit 对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com") // 添加请求地址
                .addConverterFactory(GsonConverterFactory.create())//使用gson库解析
                .build();

        IFoodService iFoodService = retrofit.create(IFoodService.class);

        Call<ResponseBody> call = iFoodService.getFoodList();
        
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: "+response.body().string());
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
