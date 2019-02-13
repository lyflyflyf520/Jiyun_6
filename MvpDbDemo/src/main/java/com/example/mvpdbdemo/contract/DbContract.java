package com.example.mvpdbdemo.contract;

import com.example.mvpdbdemo.ItemBean;

import java.util.List;

public interface DbContract {
    interface Model {// 处理业务逻辑

        void insert(ItemBean itemBean);
        void delete(String id);
        List<ItemBean> queryall();
        ItemBean queryone(String id);

    }

    interface View {  // 更新展示UI

        void updateInsert( );
        void updateDelete( );// 查询整个表里的数据
        void updateQueryall(List<ItemBean>  itemBeans);
        void updateQueryone(ItemBean itemBean);

    }

    interface Presenter { // view  model 交互的桥梁

        void insert(ItemBean itemBean);
        void delete(String id);
        void queryall();
        void queryone(String id);

    }
}
