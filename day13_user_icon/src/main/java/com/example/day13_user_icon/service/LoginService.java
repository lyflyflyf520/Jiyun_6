package com.example.day13_user_icon.service;

import com.example.day13_user_icon.bean.LoginResult;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface LoginService {


    public static final String uploadHeader="http://yun918.cn/study/public/index.php";

    @POST("/study/public/index.php/login")
    @FormUrlEncoded
    Observable<LoginResult>  getLogin(@Field("username") String username,
                                      @Field("password") String password);

//    key:“文件上传的目录”;  //可以直接设置一个上传文件目录的名称
//    uid:“41”;  //登录用户的uid
//    file:”MutilPartBody文件对象” //包含文件名字和内容
    @Multipart
    @POST("/uploadheader")
    Observable<ResponseBody>  uploadHeader(@Part("uid") RequestBody uidBody,@Part("key") RequestBody keyBody,
                                           @Part MultipartBody.Part multiPart);



}
