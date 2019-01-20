package com.example.day.service;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 *
 * ResponseBody --代表你的结果不处理，默认是字符串String  responseBody.body().string()
 * Food  代表一个具体对象
 * @Field  @FieldMap
 *
 *
 */
public interface IRegisterService {
//    http://yun918.cn/study/public/index.php/register
//    （String username,String password,String phone,String verify）
    @FormUrlEncoded  // 代表是表单形式  FormBoby
    @POST("register")
    Call<ResponseBody>  getResisterInfo(@Field("username") String user,@Field("password")  String psw,
                                        @Field("phone")  String phone,@Field("verify")  String verify);


    @FormUrlEncoded  // 代表是表单形式  FormBoby
    @POST("register")
    Call<ResponseBody>  getResisterInfo(@FieldMap()HashMap<String,String>  params);

    /**
     * URL  重新传入新的url
     * @param url
     * @return
     */
    @GET()
    Call <ResponseBody>  getWeatherInfo(@Url String url, @Query("name") String name);

    /**
     * 主要用于动态path 路径  eg:id   //    http://yun918.cn/study/public/index.php/4.0/
     * @param id
     * @return
     */
    @GET("blog/{id}")
    Call<ResponseBody> getBlog(@Path("id") int id);

//    @Headers("Authorization:APPCODE db33b75c89524a56ac94d6519e106a59")//https://kb.cnblogs.com/page/92320/
    @Multipart
    @POST("/upload")
    Call<ResponseBody>  uploadFile(@Part MultipartBody multipartBody);}
