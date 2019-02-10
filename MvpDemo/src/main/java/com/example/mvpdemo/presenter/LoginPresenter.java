package com.example.mvpdemo.presenter;

import android.util.Log;

import com.example.mvpdemo.contract.LoginContract;
import com.example.mvpdemo.model.LoginModel;
import com.example.mvpdemo.service.LoginService;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 登录模块的交互类
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View iView;

    private LoginContract.Model model;

    public LoginPresenter(LoginContract.View iView) {
        this.iView = iView;
        model = new LoginModel(iView);
    }

    private static final String TAG = "LoginPresenter";

    @Override
    public void login(String u, String psw) {
        model.loginData(u,psw);
    }

}
