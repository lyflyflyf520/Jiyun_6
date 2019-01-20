package com.example.day2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * 实现一个注册的功能
 * 并把结果打印出来
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    String reg_url ="http://yun918.cn/study/public/index.php/register/";

    Button loginBtn,regiterBtn;
    EditText userName,pswEt,checkNumEt;
    TextView resultTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginBtn = findViewById(R.id.login);
        regiterBtn = findViewById(R.id.register);

        resultTv = findViewById(R.id.result);

        userName = findViewById(R.id.user_name);
        pswEt = findViewById(R.id.psw);
        checkNumEt = findViewById(R.id.check_num);

        loginBtn.setOnClickListener(this);
        regiterBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void getRegisterNetData(String name,String psw,String checkNum){

        OkHttpClient okHttpClient = new OkHttpClient();
//（String username,String password,String phone,String verify）
        RequestBody requestBody = new FormBody.Builder()  //请求体--参数封装类
                .add("username",name)
                .add("password",psw)
                .add("phone","18787876543")
                .add("verify",checkNum)
                .build();

        Request request = new Request.Builder()// request 请求封装类
//                .addHeader("Authorization","APPCODE db33b75c89524a56ac94d6519e106a59")

//                “Authorization:APPCODE db33b75c89524a56ac94d6519e106a59”
                .post(requestBody)
                .url(reg_url).build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(TAG, "onFailure: e="+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

//                Log.d(TAG, "onResponse: response=="+response.body().string());
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultTv.setText(result);
                    }
                });
            }
        });


    }

    private void getNetRequestString(){
//        1.创建okHttpClient对象
//        2.创建request对象
//        3.获取Call 对象，，
//        4.异步或者同步请求
//        5.重新组装请求体的内容（请求地址、请求参数、请求类型，header）

        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
//        1.准备字符串内容  requestBody   FromBody
        RequestBody  requestBody = RequestBody.create(mediaType,"i am programer");


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()

                .post(requestBody)
                .url("xx").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.register:

                String name = userName.getText().toString().trim();
                String psw = pswEt.getText().toString().trim();
                String checkNum = checkNumEt.getText().toString().trim();

                getRegisterNetData(name,psw,checkNum);
                break;
            case R.id.login:
                break;
        }
    }
}
