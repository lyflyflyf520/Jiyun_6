package com.example.day9.util;

import com.example.day9.DataBean;
import com.example.day9.MyApplication;
import com.example.day9.dao.DaoSession;
import com.example.day9.dao.DataBeanDao;

import java.util.List;

/**
 * 负责 数据库的增删改查
 */
public class DbUtil {


    public void insert(DataBean dataBean) {

        DaoSession daoSession = MyApplication.getDaosession();
        if (!isExist(dataBean.getId())) {
            daoSession.insert(dataBean);
        }

    }

    public void delete(DataBean dataBean) {

        DaoSession daoSession = MyApplication.getDaosession();
        if (isExist(dataBean.getId())) {
            daoSession.delete(dataBean);
        }

    }

    public void update(DataBean dataBean) {

        DaoSession daoSession = MyApplication.getDaosession();
        daoSession.update(dataBean);

    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<DataBean> queryAll() {

        DaoSession daoSession = MyApplication.getDaosession();

        return daoSession.loadAll(DataBean.class);

    }

    /**
     * 根据条件查询 一个对象
     *
     * @param id
     * @return
     */
    public DataBean queryOne(String id) {

        DaoSession daoSession = MyApplication.getDaosession();
        return daoSession.queryBuilder(DataBean.class)
                .where(DataBeanDao.Properties.Id.eq(id))
                .build()
                .unique();

    }

    /**
     * 是否存在
     *
     * @return
     */
    public boolean isExist(String id) {
        DaoSession daoSession = MyApplication.getDaosession();
        DataBean dataBean = daoSession.queryBuilder(DataBean.class)
                .where(DataBeanDao.Properties.Id.eq(id))
                .build()
                .unique();

        if (dataBean != null) {
            return true;
        }
        return false;
    }

}
