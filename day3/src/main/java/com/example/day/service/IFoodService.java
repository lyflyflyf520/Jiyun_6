package com.example.day.service;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IFoodService {

    /**
     * MainActivity 的BaseUrl 要和接口类的方法GET的url 拼接 ；
     * 如果url全部在Baseurl里，注解的方法里要这样处理“./”
     * @return
     */
    @GET("/banner/json/")
    Call<ResponseBody> getFoodList();

}
