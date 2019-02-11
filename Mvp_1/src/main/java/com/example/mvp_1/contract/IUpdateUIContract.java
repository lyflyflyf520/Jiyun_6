package com.example.mvp_1.contract;

public interface IUpdateUIContract {
    interface Model {
        void updateUI();
    }

    interface View {
        void updateUISuccess(String result);
        void updateUIFailed(String result);
    }

    interface Presenter {
        void updateUI();
    }
}
