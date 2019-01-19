package com.jiyun_z5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jiyun_z5.service.RetroRequestService;
import com.jiyun_z5.utils.Constant;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit的使用demo
 * <p>
 * 掌握：
 * 1.get   url路径
 * 2.post  表单请求
 * 3.注解：
 * 4.数据解析  ResponseBody
 */
public class TestRetrofitActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textview1;
    @BindView(R.id.textView2)
    TextView textview2;

    private static final String TAG = "TestRetrofitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_retrofit);

        ButterKnife.bind(this);


        retroGetTest();
        retroPostTest();

    }

    private void retroPostTest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.post_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroRequestService service = retrofit.create(RetroRequestService.class);
        Call<ResponseBody> call = service.postRegister("zhangsan", "123123", "18790906767", "wyef");

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: post==response=" + response.body());
                try {
                    textview2.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d(TAG, "onFailure: post==error=" + t.getMessage());
                try {
                    textview2.setText(t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void retroGetTest() {
        // 第一步
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.food_base_url)   // 传入请求url
                .addConverterFactory(GsonConverterFactory.create()) // 结果用GSON 解析
                .build();   // 构建retrofit对象

        RetroRequestService service = retrofit.create(RetroRequestService.class);
        // 第二步
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("stage_id", "1");
        keyMap.put("limit", "20");
        keyMap.put("page", "1");
//        Call<ResponseBody> call = service.getFoodList("1");
        Call<ResponseBody> call = service.getFoodList(keyMap);
        // 第三步
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: response=" + response.body().string());

                    String result = response.body().string();
                    textview1.setText(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                Log.d(TAG, "onFailure: error=" + throwable.getMessage());
                try {
                    textview1.setText(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
