package com.jiyun.com.jiyun_z5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jiyun.com.jiyun_z5.service.RetroRequestService;

import retrofit2.Retrofit;

public class TestRetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_retrofit);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();

        RetroRequestService service = retrofit.create(RetroRequestService.class);

    }
}
