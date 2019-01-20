package com.example.day.service;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 主要用于描述请求的内容
 * 及返回的数据样式
 * get
 * post
 */
public interface IFoodService {

    /**
     * MainActivity 的BaseUrl 要和接口类的方法GET的url 拼接 ；
     * 如果url全部在Baseurl里，注解的方法里要这样处理“./”
     * @return
     */
    @GET("/banner/json/")   // http://www.wanandroid.com/banner/json/ + 当前部分url
    Call<ResponseBody> getFoodList();

}
