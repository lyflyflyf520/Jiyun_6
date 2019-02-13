package com.example.day9.contract;

import com.example.day9.DataBean;
import com.example.day9.ResultDataBean;

import java.util.List;

public interface FoodContract {
    interface Model {

        void getFoodList(String page);
        void getFoodDbList();
        void saveFoodItem(DataBean dataBean);
    }

    interface View {

        void updateFoodList(List<DataBean> dataBeanList);
        void updateFaild(String errMsg);

        void updateDbData(List< DataBean> dataBeanList);
    }

    interface Presenter {
        void getFoodList(String page);
        void getDbData();
        void saveItemData(DataBean dataBean);
    }
}
