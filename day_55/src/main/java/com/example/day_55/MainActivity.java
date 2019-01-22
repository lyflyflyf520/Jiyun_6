package com.example.day_55;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button collectBtn;
    private XRecyclerView xrecyclerview;
    MyRecyclerAdapter adapter;
    private List<ItemBean> itemBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        collectBtn = (Button) findViewById(R.id.collect_btn);
        xrecyclerview = (XRecyclerView) findViewById(R.id.xrecyclerview);
        xrecyclerview.setLayoutManager(new LinearLayoutManager(this));

         adapter = new MyRecyclerAdapter(itemBeanList,this);
         adapter.setOnLongClick(new MyRecyclerAdapter.OnLongItemClick(){

             @Override
             public void itemOnLong(int postion) {
                 // 点击Index 数据源-- 保存数据

                 insertData(itemBeanList.get(postion));

                 Toast.makeText(MainActivity.this, "保存了"+postion, Toast.LENGTH_SHORT).show();

             }
         });
        xrecyclerview.setAdapter(adapter);


        initData();

    }

    private void insertData(ItemBean itemBean){

        DaoSession daoSession = MyApplication.getDaosession();

        daoSession.insert(itemBean);
    }

    private static final String TAG = "MainActivity";
    private void initData(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.qubaobei.com/ios/cf/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IFoodService iFoodService = retrofit.create(IFoodService.class);
        Call<ResponseBody> call = iFoodService.getFoodList();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                Log.d(TAG, "onResponse: ");
                try {
                    String result = response.body().string();

                    JSONObject jsonObject = new JSONObject(result);

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i <jsonArray.length() ; i++) {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        ItemBean itemBean = new ItemBean();
                        Gson gson = new Gson();
                        itemBean =  gson.fromJson(obj.toString(),ItemBean.class);

                        itemBeanList.add(itemBean);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 更新UI

                        adapter.updateData(itemBeanList);

                    }
                });


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("onFailure", "onFailure: ");
            }
        });

    }
}
