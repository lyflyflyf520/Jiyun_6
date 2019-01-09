package com.jiyun_z5.service;

import com.jiyun_z5.bean.Food;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 *
 * retrofit 知识点
 *
 * 要掌握的
 * Get
 * Post
 * Url
 * Query
 * QueryMap
 * Field
 * FieldMap
 * Path
 * Body
 * Part
 * MiulPart
 * Header
 * Headers
 * FormUrlEncode
 *
 * @url  可以参考：https://www.jianshu.com/p/58bb50b2401f
 *
 */

public interface RetroRequestService {

    /**
     * @GET注解就表示get请求，@Query表示请求参数，将会以key=value的方式拼接在url后面
     * @param page
     * @return
     */
    @GET("/ios/cf/dish_list.php?stage_id=1&limit=20")
    Call<Food> getFoodList(@Query("page") String page);

    @GET("/ios/cf/dish_list.php?")
    Call<ResponseBody> getFoodList(@QueryMap Map<String,String> options);

    @FormUrlEncoded
    @POST("./")
    Call<ResponseBody> postRegister(@Field("username") String username,@Field("username") String password ,
                                    @Field("username") String phone,@Field("username") String verify);

}