package com.jiyun.com.jiyun_z5.service;

import com.jiyun.com.jiyun_z5.bean.Food;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 * retrofit 知识点
 *
 * 要掌握的 get post query
 *
 * GET
 *      形式：url + 参数形式。      eg: http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1
 *      注解样式
 *      入参query样式。query querymap  这两个主要适用与Url
 *
 *     @GET("/ios/cf/dish_list.php?stage_id=1&limit=20")
 *     Call<ResponseBody> getFoodList(@Query("page") String page);
 *
 *     Call<ResponseBody> getFoodList(@QueryMap Map<String, String> options);
 *
 *
 * POST
 *
 *      @FormUrlEncoded
 *      @POST("./")   post路径如果没有二级路径写“./”
 *
 *      Field FieldMap 适用与请求体上
 *
 *      @FormUrlEncoded
 *      @POST("v1/login")
 *      Call<ResponseBody> userLogin(@Field("phone") String phone, @Field("password") String password);
 *
 *      @FormUrlEncoded
 *      @POST("book/reviews")
 *      Call<String> addReviews(@FieldMap Map<String, String> fields);
 * path
 *      接收一个字符串表示接口 path ，与 baseUrl 组成完整的 Url；
 *      public interface BlogService{
 *       @GET("blog/{id}")
 *       Call<ResponseBody> getBlog(@Path("id") int id);
 *      }
 * body
 *
 *      使用 @Body 注解，指定一个对象作为 request body 。
 *      @POST("users/new")
 *      Call<User> createUser(@Body User user);
 *
 * Part  MiulPart
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * headers  使用 @Headers 注解设置固定的请求头，所有请求头不会相互覆盖，即使名字相同。
 *
 *      @Headers("Cache-Control: max-age=640000")
 *      @GET("widget/list")
 *      Call<List<Widget>> widgetList();
 *
 *       @Headers({ "Accept: application/vnd.github.v3.full+json","User-Agent: Retrofit-Sample-App"})
 *       @GET("users/{username}")Call<User>
 *      getUser(@Path("username") String username);
 *
 *
 *
 *  header
 *
 *       使用 @Header 注解---动态更新请求头，匹配的参数必须提供给 @Header ，若参数值为 null ，这个头会被省略，否则，会使用参数值的 toString 方法的返回值
 *
 *       @GET("user")
 *       Call<User> getUser(@Header("Authorization") String authorization)
 *
 *
 * FormUrlEncode  请求体是 From 表单
 *
 */

public interface RetroRequestService {

    /**
     * @GET注解就表示get请求，@Query表示请求参数，将会以key=value的方式拼接在url后面
     * @param page
     * @return
     */
    @GET("/ios/cf/dish_list.php?stage_id=1&limit=20")
    Call<Food> getFoodList(@Query("page") String page);

    @FormUrlEncoded
    @POST("./")
    Call<ResponseBody> postRegister(@Field("username") String username,@Field("username") String password ,
                                    @Field("username") String phone,@Field("username") String verify);
}