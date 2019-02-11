package com.example.mvp_34;

import android.content.Context;

import java.util.List;

public class DbUtil {

    public static void insert(Context context, DataBean dataBean) {
        DaoSession daoSession = MyApplication.getDaosession();
        if (queryByOne(context, dataBean.getId()) == null) {

            daoSession.insert(dataBean);
        }

    }

    public static void delete(Context context, DataBean dataBean) {
        DaoSession daoSession = MyApplication.getDaosession();
        daoSession.delete(dataBean);

    }

    public static void update(Context context, DataBean dataBean) {
        DaoSession daoSession = MyApplication.getDaosession();
        daoSession.update(dataBean);

    }

    public static List<DataBean> query(Context context) {
        DaoSession daoSession = MyApplication.getDaosession();

        return daoSession.queryBuilder(DataBean.class)
                .build().list();

    }

    public static DataBean queryByOne(Context context, String value) {
        DaoSession daoSession = MyApplication.getDaosession();
        DataBean dataBean = daoSession.queryBuilder(DataBean.class)
                .where(DataBeanDao.Properties.Id.eq(value))
                .build()
                .unique();

        return dataBean;
    }
}
