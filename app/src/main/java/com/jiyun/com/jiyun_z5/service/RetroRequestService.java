package com.jiyun.com.jiyun_z5.service;

import com.jiyun.com.jiyun_z5.bean.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RetroRequestService {

    @GET("users/{user}/repos")
    Call<List<Food>> listRepos(@Path("user") String user);


}