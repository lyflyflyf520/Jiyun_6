package com.example.mvp_1.model;

import com.example.mvp_1.contract.IUpdateUIContract;

import java.util.Random;

public class IUpdateUIModel implements IUpdateUIContract.Model {

    IUpdateUIContract.View iview;

    public IUpdateUIModel(IUpdateUIContract.View iview) {
        this.iview = iview;
    }

    @Override
    public void updateUI() {
        Random random = new Random();
        int result = random.nextInt(100);

        if (result % 2 == 0) {
            iview.updateUISuccess(result + "");
        } else {
            iview.updateUIFailed(result + "");
        }

    }
}
