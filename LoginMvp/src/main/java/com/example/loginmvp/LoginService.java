package com.example.loginmvp;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {


    @POST("/study/public/index.php/login")
    @FormUrlEncoded
    Observable<ResponseBody>  getLogin(@Field("username") String username,@Field("password") String password);
}
