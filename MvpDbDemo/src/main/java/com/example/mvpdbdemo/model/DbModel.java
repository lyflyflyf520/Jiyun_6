package com.example.mvpdbdemo.model;

import com.example.mvpdbdemo.ItemBean;
import com.example.mvpdbdemo.MyApplication;
import com.example.mvpdbdemo.contract.DbContract;
import com.example.mvpdbdemo.dao.DaoSession;
import com.example.mvpdbdemo.dao.ItemBeanDao;

import java.util.List;

/**
 * 处理业务逻辑
 * 增删改查
 * 是否存在
 * 查询单个数据
 */
public class DbModel implements DbContract.Model {

    private DaoSession daoSession;

    private DbContract.View iView;

    public DbModel(DbContract.View iView) {

        this.iView = iView;
        daoSession = MyApplication.getDaoSession();
    }

    @Override
    public void insert(ItemBean itemBean) {

        // 如果不存在，执行插入
        if (!isExist(itemBean.getMId())) {

            daoSession.insert(itemBean);
        }


    }

    @Override
    public void delete(String id) {
        //  查询单个数据的返回
        ItemBean itemBean = queryone(id);
        if (itemBean != null) {
            daoSession.delete(itemBean);
        }
    }

    /**
     * 查询所有数据后
     * 回调给activity 显示
     *
     * @return
     */
    @Override
    public List<ItemBean> queryall() {

        List<ItemBean> itemBeans = daoSession.loadAll(ItemBean.class);

        iView.updateQueryall(itemBeans);

        return itemBeans;


    }

    /**
     * 查询单个数据后
     * 回调给activity 显示
     *
     * @return
     */
    @Override
    public ItemBean queryone(String id) {
        ItemBean itemBean = daoSession.queryBuilder(ItemBean.class)
                .where(ItemBeanDao.Properties.MId.eq(id))
                .build()
                .unique();

        iView.updateQueryone(itemBean);
        return itemBean;
    }

    private boolean isExist(String id) {
        ItemBean itemBean = daoSession.queryBuilder(ItemBean.class)
                .where(ItemBeanDao.Properties.MId.eq(id))
                .build()
                .unique();

        if (itemBean != null) {
            return true;
        }
        return false;
    }
}
