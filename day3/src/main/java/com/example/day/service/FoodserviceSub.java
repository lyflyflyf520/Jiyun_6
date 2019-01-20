package com.example.day.service;

import com.example.day3.bean.Food;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface FoodserviceSub {


//    http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1

    /**
     * query   主要get请求传参
     * queryMap  主要get请求传参-以map形式传参 k.v
     *
     * @param page
     * @return
     */
    @GET("/ios/cf/dish_list.php?stage_id=1&limit=20") // 全部“./”
    Call<ResponseBody> getNetDataList(@Query("page") String page);

    @GET("/ios/cf/dish_list.php?")
    Call<Food> getNetDataList(@QueryMap() HashMap<String,String> params);


}
