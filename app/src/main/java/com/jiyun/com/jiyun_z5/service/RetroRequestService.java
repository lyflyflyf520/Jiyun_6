package com.jiyun.com.jiyun_z5.service;

import com.jiyun.com.jiyun_z5.bean.Food;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetroRequestService {

    /**
     * @GET注解就表示get请求，@Query表示请求参数，将会以key=value的方式拼接在url后面
     * @param page
     * @return
     */
    @GET("/ios/cf/dish_list.php?stage_id=1&limit=20")
    Call<List<Food>> getFoodList(@Query("page") String page);


}