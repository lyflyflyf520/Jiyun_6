package com.example.monthdemo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.monthdemo.R;
import com.example.monthdemo.adapter.FmPagerAdapter;
import com.example.monthdemo.bean.MsgEvent;
import com.example.monthdemo.bean.TagBean;
import com.example.monthdemo.service.HomeService;
import com.example.monthdemo.uitls.Contants;
import com.example.monthdemo.uitls.GlideApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    TabLayout mTablayout;
    ViewPager mViewpager;
    private View view;

    private boolean isCreated;
    private boolean isVisible;
    private static final String TAG = "HomeFragment";
    List<TagBean.ChannelsBean> channels;
    private List<Fragment> fragments = new ArrayList<>();
    private ImageView mUserImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, null);

        mTablayout = root.findViewById(R.id.home_tablayout);
        mViewpager = root.findViewById(R.id.home_viewpager);


        isCreated = true;

        ButterKnife.bind(this, root);
        initData();
        initView(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");

        if (isVisible) {

            Log.d(TAG, "setUserVisibleHint: userImgUrl=" + userImgUrl);
            if (!TextUtils.isEmpty(userImgUrl)) {
                mUserImg.setImageBitmap(null);
                mUserImg.setImageBitmap(BitmapFactory.decodeFile(new File(userImgUrl).toString()));
            }

        }

    }


    private static String userImgUrl = "";

    public static class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 给主界面的用户头像url 赋值
            if (intent.getAction().equals("com.monthdemo.recevier.imgurl")) {

                userImgUrl = intent.getStringExtra("imgUrl");
                Log.d(TAG, "onReceive: =接收成功=" + userImgUrl);

            }
        }
    }


    private void initData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contants.tagUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HomeService homeService = retrofit.create(HomeService.class);
        Observable<TagBean> observable = homeService.getTags();
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TagBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(TagBean value) {

                        channels = value.getChannels();


                        initTablayout(channels);
                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void initTablayout(List<TagBean.ChannelsBean> channels) {

        for (int x = 0; x < channels.size(); x++) {

            TagBean.ChannelsBean channelsBean = channels.get(x);
            ItemFragment itemFragment = new ItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("channel", channelsBean.getChannel());
            itemFragment.setArguments(bundle);

            fragments.add(itemFragment);
            mTablayout.addTab(mTablayout.newTab().setText(channels.get(x).getName()));
        }


        FmPagerAdapter fmPagerAdapter = new FmPagerAdapter(getChildFragmentManager(), fragments);
        mViewpager.setAdapter(fmPagerAdapter);

        mTablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));


    }


    /**
     * 可见的时候 被触发
     *  这个方法不能直接UI展示
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;


    }


    private void initView(View root) {
        mUserImg = (ImageView) root.findViewById(R.id.user_img);
    }
}
