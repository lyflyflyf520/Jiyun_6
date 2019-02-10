package com.example.mvpdemo.contract;

public interface LoginContract {
    interface Model {

        void loginData(String u,String psw);
    }

    interface View {

        void updateUI(String result);

        void faildUI(String result);
    }

    interface Presenter {
        void login(String u,String psw);
    }
}
