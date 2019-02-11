package com.example.mvp_1.presenter;

import com.example.mvp_1.contract.IUpdateUIContract;
import com.example.mvp_1.model.IUpdateUIModel;

public class IUpdateUIPresenter implements IUpdateUIContract.Presenter {


    private IUpdateUIModel iUpdateUIModel;

    private IUpdateUIContract.View view;

    public IUpdateUIPresenter(IUpdateUIContract.View view) {
        this.view = view;
        this.iUpdateUIModel = new IUpdateUIModel(view);
    }

    @Override
    public void updateUI() {
        iUpdateUIModel.updateUI();
    }
}
