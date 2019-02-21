package com.example.monthdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
        fragments.add(new OwnerFragment());
        fragments.add(new MallFragment());
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<titles.length;i++){
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }

        pagerAdapter = new FmPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);


    }
}
