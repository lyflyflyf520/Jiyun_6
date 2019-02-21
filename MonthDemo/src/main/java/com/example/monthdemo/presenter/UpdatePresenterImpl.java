package com.example.monthdemo.presenter;

import com.example.monthdemo.model.IModel;
import com.example.monthdemo.model.ModelImpl;
import com.example.monthdemo.view.IView;

public class UpdatePresenterImpl implements UpdatePresenter {

    ModelImpl iModel;
    IView iView;

    public UpdatePresenterImpl(IView iView) {
        iModel = new ModelImpl(iView);
        this.iView = iView;
    }

    /**
     *  p层调用m层
     *  P=UpdatePresenterImpl
     *  M=ModelImpl
     *
     */
    @Override
    public void viewToModel() {
        iModel.updateWord();

    }
}
