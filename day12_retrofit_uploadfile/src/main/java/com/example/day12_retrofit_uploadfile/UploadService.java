package com.example.day12_retrofit_uploadfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {

    /**
     *
     * @param requestBody  上传参数  post
     * @param multipartBody  上传多媒体文件  file
     * @return
     */

    public static String url = "http://yun918.cn";
    @Multipart
    @POST("/study/public/file_upload.php")
    Call<ResponseBody>  uploadFile(@Part("key") RequestBody requestBody,
                                   @Part MultipartBody.Part multipartBody);
}
