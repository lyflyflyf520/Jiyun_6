package com.example.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button registerBtn,logBtn;
    Button checkBtn;
    EditText userNameEt;
    EditText phoneNum;
    EditText pswEt,userNameLogEt,pswLogEt;
    TextView checkNum;

    /**
     * 获取的验证码
     */
    String checkNumber;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEt = findViewById(R.id.username);
        pswEt = findViewById(R.id.psw);
        checkNum = findViewById(R.id.check_num);
        phoneNum = findViewById(R.id.phone_num);

        userNameLogEt = findViewById(R.id.log_username);
        pswLogEt = findViewById(R.id.log_psw);
        logBtn = findViewById(R.id.log_btn);

        registerBtn = findViewById(R.id.register_btn);
        checkBtn = findViewById(R.id.check_btn);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCheckNum();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(){
                    public void run(){
                        getLoginData();
                    }
                }.start();

            }
        });


    }

    /**
     * 使用同步 获取登录接口的数据
     */
    private void getLoginData(){

        String user = userNameLogEt.getText().toString();
        String psw = pswLogEt.getText().toString();

        HashMap<String,String> params = new HashMap<>();
//        （String username,String password）
        params.put("username",user);
        params.put("password",psw);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yun918.cn/study/public/index.php/login/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService loginService = retrofit.create(LoginService.class);

       Call<ResponseBody> call =  loginService.getLoingData(params);

        try {
            //执行同步方法execute（）
            final Response<ResponseBody> response=  call.execute();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        checkNum.setText(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
//            Log.d(TAG, "getLoginData: "+response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 请求注册接口
     */
    private void register(){
        String user = userNameEt.getText().toString();
        String psw = pswEt.getText().toString();
        String phone = phoneNum.getText().toString();

        // 封装 参数
        HashMap<String,String>  params = new HashMap<>();
        params.put("username",user);
        params.put("password",psw);
        params.put("phone",phone);
        params.put("verify",checkNumber);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yun918.cn/study/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService loginService= retrofit.create(LoginService.class);
        Call<ResponseBody> call=  loginService.getRegisterData(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: ");
                try {
                    //获取注册接口的返回信息
                    final String result = response.body().string();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 打印 注册回调信息
                            checkNum.setText(result);
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

//    注册接口：http://yun918.cn/study/public/index.php/register
//    post  参数（String username,String password,String phone,String verify）
//
//
//    登录接口：http://yun918.cn/study/public/index.php/login
//    post 参数（String username,String password） 注：username可以是注册的用户名或手机号

    /**
     * 获取验证码
     */
    private void getCheckNum(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yun918.cn/study/public/index.php/verify/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService loginService= retrofit.create(LoginService.class);
       Call<ResponseBody> call=  loginService.getCheckNum();
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               Log.d(TAG, "onResponse: ");
               try {
                   String result = response.body().string();
                   JSONObject jsonObject = new JSONObject(result);

                   checkNumber =jsonObject.optString("data");

                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           checkNum.setText(checkNumber);
                       }
                   });

               } catch (IOException e) {
                   e.printStackTrace();
               } catch (JSONException e) {
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
