package com.example.day8.model;

import com.example.day8.contract.IUpdateListenerContract;

import java.util.Random;

public class IUpdateListenerModel implements IUpdateListenerContract.Model {
    IUpdateListenerContract.View iView;
    public IUpdateListenerModel( IUpdateListenerContract.View iView) {
        this.iView = iView;
    }

    @Override
    public void updateUI() {

        Random random = new Random();
        int result = random.nextInt(1000);

        if (result%2==0){
            iView.updateUISuccess("成功了="+result);
        }else{
            iView.updateUIFailed("失败了="+result);
        }
    }
}
