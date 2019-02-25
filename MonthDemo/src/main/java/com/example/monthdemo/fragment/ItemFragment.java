package com.example.monthdemo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.monthdemo.R;
import com.example.monthdemo.bean.WarBean;
import com.example.monthdemo.adapter.RecyclerViewAdapter;
import com.example.monthdemo.service.HomeService;
import com.example.monthdemo.uitls.Contants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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


    RecyclerView mRecyclerView;
    private boolean isCreated;
    private boolean isVisible;
    private static final String TAG = "ItemFragment";
    private String channel;
    private View view;
    private ProgressBar progressBar;
    private List<WarBean> warBeans = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.item_fragment, null);
        mRecyclerView = root.findViewById(R.id.recyclerView);
        progressBar = root.findViewById(R.id.progressbar);

        isCreated = true;

        Bundle arguments = getArguments();


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
        Observable<ResponseBody> observable = homeService.getTagList();
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressBar.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onSubscribe: "+Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            JSONObject jsonObject = new JSONObject(value.string());
                            JSONArray jsonArray = jsonObject.getJSONArray("T1348648141035");

                            for (int x=0;x<jsonArray.length();x++){
                                JSONObject obj = jsonArray.getJSONObject(x);
                                WarBean warBean = new Gson().fromJson(obj.toString(),WarBean.class);
                                warBeans.add(warBean);
                            }
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "onNext: warBeans=" + warBeans.size());
                            setData2ListView(warBeans);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 当切换fragment的时候，发现已经有数据，就使用已经请求的数据
     */
    private void updateListAdapter(){
        if(!warBeans.isEmpty()&&recyclerViewAdapter!=null){
            recyclerViewAdapter.updateDate(warBeans);
        }

    }
    RecyclerViewAdapter recyclerViewAdapter;
    private void setData2ListView(List<WarBean> data) {
          recyclerViewAdapter = new RecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(recyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerViewAdapter.updateDate(data);
    }

    /**
     * 是否可见
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser&&(warBeans!=null&&!warBeans.isEmpty())){  // 判断当前fragment 是否可见 且集合不等于null
            // 加载数据 展示数据
            updateListAdapter();

        }
    }


}
