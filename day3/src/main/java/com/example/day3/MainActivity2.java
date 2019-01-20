package com.example.day3;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.day.service.FoodserviceSub;
import com.example.day3.bean.Food;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends Activity {

    private static final String TAG = "MainActivity2";
    Button getDataBtn;
    TextView resultTv;
    MyRecyclerAdapter adapter;
    XRecyclerView xRecyclerView;
    List<Food.DataBean> dataBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);


        xRecyclerView = findViewById(R.id.xrecyclerview);
        getDataBtn = findViewById(R.id.regiter_btn);
        resultTv = findViewById(R.id.result);

        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new MyRecyclerAdapter(dataBeans, this);

        xRecyclerView.setAdapter(adapter);

        getNetDataByRetroFit();


    }

    private void getNetDataByRetroFit() {

        // 创建retrofit   url 解析json

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.qubaobei.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 创建 接口的实例对象   FoodserviceSub
        FoodserviceSub foodserviceSub = retrofit.create(FoodserviceSub.class);

        // 获取请求的call 对象
//        Call<ResponseBody>  call = foodserviceSub.getNetDataList("1");
//        stage_id=1&limit=20&page=1
        HashMap<String, String> parms = new HashMap<>();
        parms.put("stage_id", "1");
        parms.put("limit", "20");
        parms.put("page", "2");
        Call<Food> call = foodserviceSub.getNetDataList(parms);

        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                try {
                    final List<Food.DataBean> dataBeans = response.body().getData();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            adapter.updateData(dataBeans);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                Log.d(TAG, "onResponse: "+);


            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}
