package com.example.mvp_34.contract;

import com.example.mvp_34.DataBean;
import com.example.mvp_34.Result;

import java.util.List;

public interface IFoodListContract {
    interface Model {
        void getListNetData();
    }

    interface View {
        void updateListData(List<DataBean> data);
        void updateListFaild(String value);
    }

    interface Presenter {

        void getListData();
    }
}
