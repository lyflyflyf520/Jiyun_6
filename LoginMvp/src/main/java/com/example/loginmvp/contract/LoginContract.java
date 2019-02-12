package com.example.loginmvp.contract;

public interface LoginContract {
    interface Model {

        void loginData();
    }

    interface View {

        void loginJump(String result);

        void loginError(String result);
    }

    interface Presenter {

        void getLoginData();
    }
}
