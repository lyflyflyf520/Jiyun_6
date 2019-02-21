package com.example.monthdemo.service;


import com.example.monthdemo.ItemBean;
import com.example.monthdemo.TagBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface HomeService {

    @GET("public/index.php/newchannel")
    Observable<TagBean> getTags();

    @Headers("Authorization:APPCODE db33b75c89524a56ac94d6519e106a59")
    @GET("toutiao/index")
    Observable<ItemBean> getTagList(@Query("type") String type);


}
