package com.jiyun_z5.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.jiyun_z5.bean.BannerItem;

import com.jiyun_z5.dao.BannerItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bannerItemDaoConfig;

    private final BannerItemDao bannerItemDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bannerItemDaoConfig = daoConfigMap.get(BannerItemDao.class).clone();
        bannerItemDaoConfig.initIdentityScope(type);

        bannerItemDao = new BannerItemDao(bannerItemDaoConfig, this);

        registerDao(BannerItem.class, bannerItemDao);
    }
    
    public void clear() {
        bannerItemDaoConfig.clearIdentityScope();
    }

    public BannerItemDao getBannerItemDao() {
        return bannerItemDao;
    }

}
