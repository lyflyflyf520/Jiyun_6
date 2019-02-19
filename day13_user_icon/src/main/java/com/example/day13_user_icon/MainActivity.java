package com.example.day13_user_icon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.day13_user_icon.bean.LoginResult;
import com.example.day13_user_icon.service.LoginService;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 输入名称
     */
    private EditText mUsername;
    /**
     * 输入密码
     */
    private EditText mPsw;
    /**
     * 登录
     */
    private Button mLogin;
    /**
     * Hello World!
     */
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

    }

    private void initView() {
        mUsername = (EditText) findViewById(R.id.username);
        mPsw = (EditText) findViewById(R.id.psw);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login:
                loginData();
                break;
        }
    }
    public static String loginUrl ="http://yun918.cn";

    private static final String TAG = "MainActivity";
    /**
     * 登录
     * 1.retrofit
     * 2.rxjava
     *
     *
     */
    public void loginData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(loginUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService loginService =retrofit.create(LoginService.class);

        Observable<LoginResult> observable = loginService.getLogin("111","111");

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }
                    @Override
                    public void onNext(LoginResult value) {

                        Log.d(TAG, "onNext: ");

                        try {
                            // 判断是否登录成功  200

                            if (value.getCode()==200){
                                LoginResult.DataBean dataBean = value.getData().get(0);

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("uid",dataBean.getUid());
                                intent.putExtra("result",dataBean.toString());
                                startActivity(intent);
                            }else{
                                mResult.setText("登录失败");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("uid","728");
                        intent.putExtra("result","\"id\":733,\"uid\":\"728\",\"name\":\"zz1\",\"password\":\"123456\",\"age\":18,\"sex\":1,\"phone\":\"18716666666\",\"header\":null");
                        startActivity(intent);
                        mResult.setText(e.getMessage());
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: ");
                    }
                });

    }
}
