package com.example.mvpdemo.model;

import android.util.Log;

import com.example.mvpdemo.contract.LoginContract;
import com.example.mvpdemo.service.LoginService;

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

    private LoginContract.View iView;

    public LoginModel(LoginContract.View iView) {
        this.iView = iView;
    }

    private static final String TAG = "LoginModel";
    @Override
    public void loginData(String u,String psw) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginService.loginUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        LoginService loginService = retrofit.create(LoginService.class);

//        Call<ResponseBody> call = loginService.getLoginStatus("hh","dd");


        Observable<ResponseBody> observable = loginService.getLogin(u,psw);

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ResponseBody value) {


                        try {
                            iView.updateUI(value.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                        iView.faildUI(e.getMessage());
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: ");
                    }
                });

    }
}
