package com.example.day9.model;

import android.util.Log;

import com.example.day9.DataBean;
import com.example.day9.IFoodService;
import com.example.day9.ResultDataBean;
import com.example.day9.contract.FoodContract;
import com.example.day9.util.DbUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 真实处理业务逻辑的对象-获取网络数据
 */
public class FoodModel implements FoodContract.Model {


    private static final String TAG = "FoodModel";
    FoodContract.View foodView;
    DbUtil dbUtil;
    public FoodModel( FoodContract.View foodView) {
        this.foodView = foodView;
         dbUtil = new DbUtil();
    }

    @Override
    public void getFoodList(String page) {

//        rxjava retrofit

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IFoodService.url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IFoodService iFoodService =retrofit.create(IFoodService.class);
        Observable<ResultDataBean> observable = iFoodService.getFoodList(page);

        observable.subscribeOn(Schedulers.newThread()) // 处理逻辑发生在子线程
                .observeOn(AndroidSchedulers.mainThread()) // 处理结果发生在主线程
                .subscribe(new Observer<ResultDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultDataBean value) {

                        foodView.updateFoodList(value.getData());
                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                        foodView.updateFaild(e.getMessage());

                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取全部数据库的数据
     */
    @Override
    public void getFoodDbList() {
        List<DataBean>  dataBeanList = dbUtil.queryAll();
        if (dataBeanList!=null&&!dataBeanList.isEmpty()){
            foodView.updateDbData(dataBeanList);
        }
    }

    /**
     * 保存 某个数据对象
     * @param dataBean
     */
    @Override
    public void saveFoodItem(DataBean dataBean) {
        dbUtil.insert(dataBean);
    }
}
