package com.example.monthdemo.service;


import com.example.monthdemo.bean.TagBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface HomeService {

    @GET("public/index.php/newchannel")
    Observable<TagBean> getTags();

    @Headers("User-Agent: Mozilla/5.0 (Linux; X11)")
    @GET("T1348648141035/0-20.html")
    Observable<ResponseBody> getTagList();


}
