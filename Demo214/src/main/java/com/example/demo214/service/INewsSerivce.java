package com.example.demo214.service;

import com.example.demo214.ResultBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface INewsSerivce {

    public static final String newUrl = "http://c.m.163.com";
    @GET("/nc/article/headline/T1348647909107/0-20.html")
    Observable<ResultBean>  getNewsData();
}
