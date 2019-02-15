package com.example.frescodemo;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

public class MainActivity extends AppCompatActivity {

    private SimpleDraweeView mMyImageView;
    private SimpleDraweeView mMyImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        normalMethod();
        listenerMethod();
    }

    private void initView() {
        mMyImageView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        mMyImageView2 = (SimpleDraweeView) findViewById(R.id.my_image_view2);
    }

    private static final String TAG = "MainActivity";

    /**
     * 普通用法
     */
    public void normalMethod() {

        Uri uri = Uri.parse("http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png");

        mMyImageView.setImageURI(uri);
    }

    /**
     * 使用监听器
     */
    private void listenerMethod() {
        Uri uri = Uri.parse("http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg");
        DraweeController controller = Fresco.newDraweeControllerBuilder()  // 构建一个控制器 ，来监听图片的加载
                .setControllerListener(controllerListener)
                .setUri(uri)
                .build();
        mMyImageView2.setController(controller);
    }

    ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {

        @Override
        public void onRelease(String id) {  // 释放 资源
            super.onRelease(id);
            Log.d(TAG, "onRelease: ");
        }
        @Override
        public void onSubmit(String id, Object callerContext) {  // 提交图片的请求，初始化工作
            super.onSubmit(id, callerContext);
            Log.d(TAG, "onSubmit: ");
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) { // 最后图片的显示
            super.onFinalImageSet(id, imageInfo, animatable);
            Log.d(TAG, "onFinalImageSet: ");
        }

        @Override
        public void onFailure(String id, Throwable throwable) {  // 失败
            super.onFailure(id, throwable);
            Log.d(TAG, "onFailure: ");
        }
    };
}
