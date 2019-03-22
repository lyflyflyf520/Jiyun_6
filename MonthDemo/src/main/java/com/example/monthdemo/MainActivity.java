package com.example.monthdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monthdemo.adapter.FmPagerAdapter;
import com.example.monthdemo.bean.MsgEvent;
import com.example.monthdemo.fragment.HomeFragment;
import com.example.monthdemo.fragment.MallFragment;
import com.example.monthdemo.fragment.OwnerFragment;
import com.example.monthdemo.presenter.UpdatePresenterImpl;
import com.example.monthdemo.view.IView;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_SECURE_SETTINGS;

public class MainActivity extends AppCompatActivity implements IView {

    private TextView mWords;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FmPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"首页","购物车","我"};
//    private ImageView userImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE,CAMERA
                    };
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

        initView();

        initMvp();



//        initReceiver();

    }

    /**
     * view   是当前activity
     * Presenter UpdatePresenterImpl
     *
     * 实现view 调用presenter
     */
    private void initMvp(){
        // 创建p层，p调用m层，来达到交互的效果

        UpdatePresenterImpl updatePresenter = new UpdatePresenterImpl(this);

        updatePresenter.viewToModel();

    }
//    HomeFragment.MyReceiver myReceiver;
//    public void initReceiver(){
//        // 1. 实例化BroadcastReceiver子类 &  IntentFilter
//         myReceiver = new HomeFragment.MyReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//
//        // 2. 设置接收广播的类型
//        intentFilter.addAction("com.monthdemo.recevier.imgurl");
//
//        // 3. 动态注册：调用Context的registerReceiver（）方法
//       registerReceiver(myReceiver, intentFilter);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(myReceiver);

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void updateInfo(String info) {

//        mWords.setText(info);
    }

    private void initView() {

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        userImg = (ImageView) findViewById(R.id.user_img);


        fragments.add(new HomeFragment());
        fragments.add(new MallFragment());
        fragments.add(new OwnerFragment());

        for(int i=0;i<titles.length;i++){
            if (i==0){
                tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home).setText(titles[i]));
            }else if(i==1){
                tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.mall).setText(titles[i]));
            }else if(i==2){
                tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.owner).setText(titles[i]));
            }

        }
        tabLayout.getTabAt(0).select();

        pagerAdapter = new FmPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager supportFragmentManager = getSupportFragmentManager();

        // 接收onActivityResult 方式
        List<Fragment> fragments = supportFragmentManager.getFragments();
        for (Fragment fragment :fragments){
            if (fragment instanceof OwnerFragment ){

                ((OwnerFragment)fragment).onActivityResult(requestCode, resultCode, data);
            }
        }

    }
}
