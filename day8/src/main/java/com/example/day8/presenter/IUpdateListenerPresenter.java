package com.example.day8.presenter;

import com.example.day8.contract.IUpdateListenerContract;
import com.example.day8.model.IUpdateListenerModel;

public class IUpdateListenerPresenter implements IUpdateListenerContract.Presenter {

    // 接收 view的实现类
    IUpdateListenerContract.View iView;
    // 接收 model 的实现类
    IUpdateListenerModel iUpdateListenerModel;

    public IUpdateListenerPresenter(IUpdateListenerContract.View iView) {
        this.iView = iView;

        iUpdateListenerModel = new IUpdateListenerModel(iView);

    }

    @Override
    public void updateUIByRadom() {

        iUpdateListenerModel.updateUI();

    }
}
