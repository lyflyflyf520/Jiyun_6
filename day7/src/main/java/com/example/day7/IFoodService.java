package com.example.day7;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IFoodService {


//    public static String img_url ="http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png";
//    public static String home_list_url ="http://www.wanandroid.com/banner/json";
//    public static final String  food_url="http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";
//    public static final String  food_base_url="http://www.qubaobei.com";
//    public static final String  post_url ="http://yun918.cn/study/public/index.php/register/";
//

    @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
    Call<ResponseBody> getFoodList();
}
