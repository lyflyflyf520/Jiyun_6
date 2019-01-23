package com.example.day7;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IFoodService {


//    public static String img_url ="http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png";
//    public static String home_list_url ="http://www.wanandroid.com/banner/json";
//    public static final String  food_url="http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";
//    public static final String  food_base_url="http://www.qubaobei.com";
//    public static final String  post_url ="http://yun918.cn/study/public/index.php/register/";
//


    @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
    Call<ResponseBody> getFoodList();

    @GET("http://yun918.cn/study/public/index.php/newchannel")
    Call<Channel> getTags();

    @Headers("Authorization:APPCODE db33b75c89524a56ac94d6519e106a59")
    @GET("toutiao/index")
    Call<ResponseBody> getTagList(@Query("type") String type);
}
