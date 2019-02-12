package com.example.day8.contract;

/**
 * 需求：点击 生成随机数 更新到UI
 */
public interface IUpdateListenerContract {
    interface Model {  // 处理业务逻辑

        void updateUI();
    }

    interface View {  // 更新UI

        void updateUISuccess(String result);
        void updateUIFailed(String result);
    }

    interface Presenter { // view model 之间的交互
        void updateUIByRadom();
    }
}
