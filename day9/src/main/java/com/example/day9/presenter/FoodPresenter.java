package com.example.day9.presenter;

import com.example.day9.DataBean;
import com.example.day9.contract.FoodContract;
import com.example.day9.model.FoodModel;

public class FoodPresenter implements FoodContract.Presenter {

    // 创建Model实例化对象
    // 传入view的实例对象--activity

    FoodModel foodModel;
    FoodContract.View foodView;

    public FoodPresenter(FoodContract.View foodView) {
        this.foodView = foodView;
        foodModel = new FoodModel(foodView);
    }

    @Override
    public void getFoodList(String page) {
        foodModel.getFoodList(  page);
    }

    @Override
    public void getDbData() {

        foodModel.getFoodDbList();
    }

    @Override
    public void saveItemData(DataBean dataBean) {
        foodModel.saveFoodItem(dataBean);

    }
}
