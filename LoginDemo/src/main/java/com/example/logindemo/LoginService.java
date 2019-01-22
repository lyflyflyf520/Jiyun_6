package com.example.logindemo;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {

    @GET("./")
    Call<ResponseBody> getCheckNum();

    @FormUrlEncoded
    @POST("public/index.php/register")
    Call<ResponseBody> getRegisterData(@FieldMap()HashMap<String,String> params);

    @FormUrlEncoded
    @POST("./")
    Call<ResponseBody> getLoingData(@FieldMap()HashMap<String,String> params);


}
