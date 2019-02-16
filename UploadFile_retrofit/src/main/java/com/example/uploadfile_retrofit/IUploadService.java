package com.example.uploadfile_retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IUploadService {

    public static String uploadUrl ="http://yun918.cn";
    @Multipart
    @POST("/study/public/file_upload.php")
    Call<ResponseBody> uploadFile(@Part("key") RequestBody requestBody,@Part MultipartBody.Part multipartBody);




}
