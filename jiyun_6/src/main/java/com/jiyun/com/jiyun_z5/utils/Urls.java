package com.jiyun.com.jiyun_z5.utils;

import com.jiyun.com.jiyun_z5.DbDaoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Urls {

    public static final String PAKEAGE_NAME = "com.jiyun.com.jiyun_z5.";
    public static final List<HashMap<String, String>> classList = new ArrayList<>();

    static {
        HashMap<String, String> className = new HashMap<>();

        className.put("XRecyclerViewActivity", "1.okhttp $ xRecyclerView GET POST 请求头 拦截器");
        classList.add(className);

        className = new HashMap<>();
        className.put("TestOkhttpPostActivity", "2.展示okhttp header 表单 文件 String--拦截器");
        classList.add(className);

        className = new HashMap<>();
        className.put("TestRetrofitActivity", "retrofit的get方式获取demo");
        classList.add(className);

        className = new HashMap<>();
        className.put(DbDaoActivity.class.getSimpleName(), "演示greendao的操作流程，增删改查");
        classList.add(className);
    }
}
