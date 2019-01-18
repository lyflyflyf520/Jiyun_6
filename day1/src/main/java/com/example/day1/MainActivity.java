package com.example.day1;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;


import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 *
 * 想要实现列表
 * 通过网络数据接口获取
 * 然后把数据赋值给列表组件
 * 重点：线程的问题
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    public static final String  food_url="http://www.wanandroid.com/banner/json";

    private static final String TAG = "MainActivity";
    XRecyclerView xRecyclerView;



    @SuppressLint("HandlerLeak")
    Handler handler= new Handler(){
        public void handleMessage(Message msg){
            if (msg.what ==99){

                List<ItemBean> itemBeans = (List<ItemBean>) msg.obj;

                recyclerViewAdapter.updateDate(itemBeans);

            }
        }
    };
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        xRecyclerView = findViewById(R.id.recyclerView);

        recyclerViewAdapter = new RecyclerViewAdapter(this);

        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        xRecyclerView.addItemDecoration(new XRecyclerView.DividerItemDecoration(this, XRecyclerView.DividerItemDecoration.VERTICAL));

        //添加Android自带的分割线
        xRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        xRecyclerView.setAdapter(recyclerViewAdapter);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });

        getNetData();
    }

    private void getNetData(){
        OkHttpClient okHttpClient = new OkHttpClient(); // 创建client对象

        Request request = new Request.Builder().url(food_url).build(); // 创建 请求数据的参数对象

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {  // 调取异步方法，获取数据
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();

                List<ItemBean> itemBeans = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int x=0;x<jsonArray.length();x++){
                        ItemBean itemBean = new ItemBean();
                        JSONObject obj = jsonArray.getJSONObject(x);

                        itemBean.setTitle(obj.getString("title"));
                        itemBean.setImagePath(obj.getString("imagePath"));
                        itemBeans.add(itemBean);
                    }

                    Message msg = Message.obtain();
                    msg.what=99;
                    msg.obj = itemBeans;
                    handler.sendMessage(msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.d(TAG, "onResponse: reuslt=="+result);
            }
        });
    }
}
