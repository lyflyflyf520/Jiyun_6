package com.example.mvp_34.model;

import android.util.Log;

import com.example.mvp_34.Result;
import com.example.mvp_34.contract.IFoodListContract;
import com.example.mvp_34.service.IFoodService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 处理网络请求数据
 */
public class IFoodListModel implements IFoodListContract.Model {

    private static final String TAG = "IFoodListModel";
    IFoodListContract.View view;

    public IFoodListModel(IFoodListContract.View view) {
        this.view = view;
    }

    @Override
    public void getListNetData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IFoodService.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        IFoodService iFoodService = retrofit.create(IFoodService.class);
        Observable<Result> observable = iFoodService.getFoodList("1");

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Result value) {
                        view.updateListData(value.getData());

                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.updateListFaild(e.getMessage());
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: ");
                    }
                });

    }
}
