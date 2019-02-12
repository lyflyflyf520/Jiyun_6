package com.example.loginmvp.presenter;

import com.example.loginmvp.contract.LoginContract;
import com.example.loginmvp.model.LoginModel;

public class LoginPresenter implements LoginContract.Presenter {

    // model
    LoginModel loginModel;
    LoginContract.View iView;

    public LoginPresenter( LoginContract.View iView) {
        this.iView = iView;
         loginModel = new LoginModel(iView);
    }

    // view

    @Override
    public void getLoginData() {

        loginModel.loginData();

    }
}
