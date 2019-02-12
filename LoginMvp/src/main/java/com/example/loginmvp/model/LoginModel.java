package com.example.loginmvp.model;

import android.util.Log;

import com.example.loginmvp.LoginService;
import com.example.loginmvp.contract.LoginContract;

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

public class LoginModel implements LoginContract.Model {

    private static final String TAG = "LoginModel";
    public static String loginUrl ="http://yun918.cn";

    LoginContract.View iView;

    public LoginModel(LoginContract.View iView) {
        this.iView = iView;
    }

    @Override
    public void loginData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(loginUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService loginService =retrofit.create(LoginService.class);

        Observable<ResponseBody>  observable = loginService.getLogin("111","111");

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ResponseBody value) {

                        Log.d(TAG, "onNext: ");

                        try {
                            iView.loginJump(value.string());


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        iView.loginError(e.getMessage());
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: ");
                    }
                });

    }
}
