package com.example.day9;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFoodService {

    public static final String url ="http://www.qubaobei.com";

    @GET("/ios/cf/dish_list.php?stage_id=1&limit=20")
    Observable<ResultDataBean> getFoodList(@Query("page") String page);
}
