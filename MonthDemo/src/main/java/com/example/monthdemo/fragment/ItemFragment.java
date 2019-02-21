package com.example.monthdemo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monthdemo.ItemBean;
import com.example.monthdemo.R;
import com.example.monthdemo.adapter.RecyclerViewAdapter;
import com.example.monthdemo.service.HomeService;
import com.example.monthdemo.uitls.Contants;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemFragment extends Fragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private boolean isCreated;
    private boolean isVisible;
    private static final String TAG = "ItemFragment";
    private String channel;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.item_fragment, null);

        Bundle arguments = getArguments();

        channel = arguments.getString("channel");

        isCreated = true;

        ButterKnife.bind(this, root);
        initData();
        return root;
    }

    private void initData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contants.tagIndexUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        HomeService homeService = retrofit.create(HomeService.class);
        Observable<ItemBean> observable = homeService.getTagList(channel);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ItemBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ItemBean value) {


                        try {
//                            Log.d(TAG, "onNext: " + value.string());

                            setData2ListView(value.getResult().getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    private void setData2ListView(List<ItemBean.ResultBean.DataBean> data) {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.updateDate(data);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }


    @OnClick(R.id.recyclerView)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.recyclerView:
                break;
        }
    }
}
