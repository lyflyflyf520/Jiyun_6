package com.example.monthdemo.uitls;

import android.util.Log;

import com.example.monthdemo.MyApplication;
import com.example.monthdemo.bean.WarBean;
import com.example.monthdemo.dao.DaoSession;
import com.example.monthdemo.dao.WarBeanDao;

import java.util.List;


public class DbUtils {

    private static final String TAG = "DbUtils";
    public static void insert(WarBean warBean) {

        if (queryOne(warBean)==null){
            DaoSession daoSession = MyApplication.getDaosession();
            daoSession.insert(warBean);
        }

    }

    public static void delete(WarBean warBean) {
        try {
            WarBean warBean1 = queryOne(warBean);
            Log.d(TAG, "delete: bean="+warBean1.getTitle());
            if (warBean1!=null){
                DaoSession daoSession = MyApplication.getDaosession();
                daoSession.delete(warBean);
            }
        } catch (Exception e) {
            Log.d(TAG, "delete: e="+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 查询多个对象数据
     *
     * @return List<WarBean>
     */
    public static List<WarBean> queryAll() {
        DaoSession daoSession = MyApplication.getDaosession();

        return daoSession.loadAll(WarBean.class);
    }

    /**
     * 查询单个对象数据
     *
     * @param warBean
     * @return
     */
    public static WarBean queryOne(WarBean warBean) {
        DaoSession daoSession = MyApplication.getDaosession();

        return daoSession.queryBuilder(WarBean.class)
                .where(WarBeanDao.Properties.Postid.eq(warBean.getPostid()))
                .build()
                .unique();
    }
}
