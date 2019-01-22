package com.example.day_55;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IFoodService {
    @GET("dish_list.php?stage_id=1&limit=20&page=1")
    Call<ResponseBody>   getFoodList();
}
