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

    String imgUrl="http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        GlideApp.with(this)
                .load(imgUrl)
                .centerCrop()
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

    private void initView() {
        mImg = (ImageView) findViewById(R.id.img);
    }
}
