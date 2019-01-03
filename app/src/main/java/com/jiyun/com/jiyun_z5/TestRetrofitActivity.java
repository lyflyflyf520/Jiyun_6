package com.jiyun.com.jiyun_z5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jiyun.com.jiyun_z5.bean.Food;
import com.jiyun.com.jiyun_z5.service.RetroRequestService;
import com.jiyun.com.jiyun_z5.utils.Constant;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.food_base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroRequestService service = retrofit.create(RetroRequestService.class);

        Call<ResponseBody> call  = service.getFoodList("1");

        call.enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: response="+response.body());
                try {
                    textview1.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d(TAG, "onFailure: error="+t.getMessage());
                try {
                    textview1.setText(t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




}
