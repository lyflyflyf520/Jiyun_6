package com.example.mvpdbdemo.presenter;

import com.example.mvpdbdemo.ItemBean;
import com.example.mvpdbdemo.contract.DbContract;
import com.example.mvpdbdemo.model.DbModel;

/**
 *  view model  交互的桥梁（中介）
 */
public class DbPresenter implements DbContract.Presenter {

    DbModel dbModel ;
    DbContract.View iview;

    public DbPresenter(DbContract.View iview) {
        this.iview = iview;
        this.dbModel =new DbModel(iview);
    }

    @Override
    public void insert(ItemBean itemBean) {
        dbModel.insert(itemBean);
    }

    @Override
    public void delete(String id) {
        dbModel.delete(id);
    }

    @Override
    public void queryall() {

        dbModel.queryall();
    }

    @Override
    public void queryone(String id) {

        dbModel.queryone(id);
    }
}
