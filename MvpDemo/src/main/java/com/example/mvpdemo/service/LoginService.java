package com.example.mvpdemo.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    public static String loginUrl ="http://yun918.cn";
    @POST("/study/public/index.php/login")
    @FormUrlEncoded
    Call<ResponseBody>  getLoginStatus(@Field("username") String username,@Field("password") String password);

    @POST("/study/public/index.php/login")
    @FormUrlEncoded
    Observable<ResponseBody>  getLogin(@Field("username") String username,@Field("password") String password);
}
