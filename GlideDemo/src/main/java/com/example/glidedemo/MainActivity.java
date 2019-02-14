package com.example.glidedemo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    /**
     * Hello World!
     */
    private ImageView mImg;
    private ImageView mImg1;
    private ImageView mImg2;

    String imgUrl="https://www.qubaobei.com/ios/cf/uploadfile/132/9/8289.jpg";
//    String imgUrl="https://i1.mifile.cn/f/i/2018/mi8/summary/index2.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        loadCircleImage(this,imgUrl,mImg1);
        loadCustRoundCircleImage(this,imgUrl,mImg2, RoundedCornersTransformation.CornerType.ALL);

        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存


        GlideApp.with(this)
                .load(imgUrl)
                .centerCrop()
                .apply(mRequestOptions)
                .placeholder(R.drawable.ic_launcher_background)//加载中显示的图片
                .error(R.drawable.ic_launcher_foreground)// 错误后显示的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 执行的缓存策略
                .onlyRetrieveFromCache(true)   //使用缓存
                .into(mImg);

    }

    private void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        mImg1 = (ImageView) findViewById(R.id.img1);
        mImg2 = (ImageView) findViewById(R.id.img2);
    }
    /*
     *加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）
     */
    public static void loadCustRoundCircleImage(Context context, String url, ImageView imageView, RoundedCornersTransformation.CornerType type) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(android.R.color.white)
                .error(android.R.color.black)
                //.priority(Priority.HIGH)
                .bitmapTransform(new RoundedCornersTransformation(45, 0, type))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(url).apply(options).into(imageView);
    }
    /*
     *加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(android.R.color.white)
                .error(android.R.color.black)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

}
