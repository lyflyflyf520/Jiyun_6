package com.example.mvp_34.presenter;

import com.example.mvp_34.contract.IFoodListContract;
import com.example.mvp_34.model.IFoodListModel;

public class IFoodListPresenter implements IFoodListContract.Presenter {


    private IFoodListContract.View iView;

    private IFoodListModel iFoodListModel;

    public IFoodListPresenter(IFoodListContract.View iView) {
        this.iView = iView;
        iFoodListModel = new IFoodListModel(iView);
    }

    @Override
    public void getListData() {
        iFoodListModel.getListNetData();
    }
}
