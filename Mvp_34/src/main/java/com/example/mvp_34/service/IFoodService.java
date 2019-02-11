package com.example.mvp_34.service;

import com.example.mvp_34.Result;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFoodService {

    public static String url ="https://www.qubaobei.com";


    @GET("/ios/cf/dish_list.php?stage_id=1&limit=20")
    Observable<Result> getFoodList(@Query("page") String page);
}
