package com.example.monthdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.monthdemo.adapter.FmPagerAdapter;
import com.example.monthdemo.fragment.HomeFragment;
import com.example.monthdemo.fragment.MallFragment;
import com.example.monthdemo.fragment.OwnerFragment;
import com.example.monthdemo.presenter.UpdatePresenterImpl;
import com.example.monthdemo.view.IView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {

    private TextView mWords;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FmPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"首页","购物车","我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initMvp();


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


    @Override
    public void updateInfo(String info) {

//        mWords.setText(info);
    }

    private void initView() {

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);


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

        List<Fragment> fragments = supportFragmentManager.getFragments();
        for (Fragment fragment :fragments){
            if (fragment instanceof OwnerFragment ){

                ((OwnerFragment)fragment).onActivityResult(requestCode, resultCode, data);
            }
        }

    }
}
