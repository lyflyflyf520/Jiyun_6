package com.example.monthdemo.model;

import com.example.monthdemo.view.IView;

/**
 * 处理业务逻辑
 * eg：请求网络，耗时任务
 */
public class ModelImpl implements IModel {

    IView iView;// activity

    public ModelImpl(IView iView) {
        this.iView = iView;
    }

    /**
     * 说一句话的功能
     */
    @Override
    public void updateWord() {

        String words="我是mvp的model层";

        // 通过给view
        iView.updateInfo(words);

    }
}
