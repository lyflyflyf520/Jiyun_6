package com.example.mvpdbdemo.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.mvpdbdemo.ItemBean;

import com.example.mvpdbdemo.dao.ItemBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig itemBeanDaoConfig;

    private final ItemBeanDao itemBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        itemBeanDaoConfig = daoConfigMap.get(ItemBeanDao.class).clone();
        itemBeanDaoConfig.initIdentityScope(type);

        itemBeanDao = new ItemBeanDao(itemBeanDaoConfig, this);

        registerDao(ItemBean.class, itemBeanDao);
    }
    
    public void clear() {
        itemBeanDaoConfig.clearIdentityScope();
    }

    public ItemBeanDao getItemBeanDao() {
        return itemBeanDao;
    }

}
