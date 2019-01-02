package com.jiyun.com.jiyun_z5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jiyun.com.jiyun_z5.bean.Food;
import com.jiyun.com.jiyun_z5.service.RetroRequestService;
import com.jiyun.com.jiyun_z5.utils.Constant;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit的使用demo
 */
public class TestRetrofitActivity extends AppCompatActivity {

    private static final String TAG = "TestRetrofitActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_retrofit);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.food_base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroRequestService service = retrofit.create(RetroRequestService.class);

        Call<List<Food>> call  = service.getFoodList("1");

        call.enqueue(new Callback<List<Food>>(){

            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                Log.d(TAG, "onResponse: response="+response.body());
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {

                Log.d(TAG, "onFailure: error="+t.getMessage());
            }
        });
    }




}
