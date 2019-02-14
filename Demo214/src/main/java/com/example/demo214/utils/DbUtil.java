package com.example.demo214.utils;

import com.example.demo214.MyApplication;
import com.example.demo214.T1348647909107Bean;
import com.example.demo214.dao.DaoSession;
import com.example.demo214.dao.T1348647909107BeanDao;

import java.util.List;

public class DbUtil {

    DaoSession daoSession;

    public DbUtil() {
        this.daoSession = MyApplication.getDaoSession();
    }

    public void insert(T1348647909107Bean t1348647909107Bean) {

        if (!isExist(t1348647909107Bean)) {

            daoSession.insert(t1348647909107Bean);
        }
    }

    public List<T1348647909107Bean> getDbAll() {

        return daoSession.loadAll(T1348647909107Bean.class);
    }

    public void deleteOne(T1348647909107Bean t1348647909107Bean) {
        if (isExist(t1348647909107Bean)) {

            daoSession.delete(t1348647909107Bean);
        }

    }

    public boolean isExist(T1348647909107Bean tBean) {

        T1348647909107Bean t1348647909107Bean = getOneBean(tBean.getDocid());
        if (t1348647909107Bean != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取单个数据
     *
     * @return
     */
    public T1348647909107Bean getOneBean(String docid) {
        T1348647909107Bean t1348647909107Bean = daoSession.queryBuilder(T1348647909107Bean.class)
                .where(T1348647909107BeanDao.Properties.Docid.eq(docid))
                .build()
                .unique();

        return t1348647909107Bean;
    }
}
