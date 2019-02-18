package com.example.glidedemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class MainActivity extends AppCompatActivity {

    /**
     * Hello World!
     */
    private ImageView mImg;

    private String imgUrl = "http://cms-bucket.ws.126.net/2019/02/15/97392c98fe22485a8a2458ba9dd907d9.jpeg";
    /**
     * Hello World!
     */
    private ImageView mImg1;
    /**
     * Hello World!
     */
    private ImageView mImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        cirCleImg();

        cornersCleImg();

        GlideApp.with(this)
                .load(imgUrl)
                .fitCenter()
                .placeholder(R.drawable.ic_launcher_background)//加载中显示的图片
                .error(R.drawable.ic_launcher_foreground)// 错误后显示的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)// 执行的缓存策略
                .onlyRetrieveFromCache(true)   //使用缓存
                .listener(new RequestListener<Drawable>() {// 图片加载 监听
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mImg);

    }

    /**
     * 圆形图片
     */
    public void cirCleImg() {

        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .skipMemoryCache(true);//不做内存缓存

        GlideApp.with(this)
                .load(imgUrl)
                .centerCrop()
                .apply(mRequestOptions)
                .placeholder(R.drawable.ic_launcher_background)//加载中显示的图片
                .error(R.drawable.ic_launcher_foreground)// 错误后显示的图片
                .into(mImg1);
    }
    /**
     * 圆角图片
     */
    public void cornersCleImg() {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(20);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .override(300, 300);
        GlideApp.with(this)
                .load(imgUrl)
                .apply(options)
                .into(mImg2);
    }


    private void initView() {
        mImg = (ImageView) findViewById(R.id.img);
        mImg1 = (ImageView) findViewById(R.id.img1);
        mImg2 = (ImageView) findViewById(R.id.img2);
    }
}
