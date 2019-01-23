package com.example.day7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                Response<ResponseBody> response = initRetrofit();
                e.onNext(response.body().string());
                e.onComplete();

            }
        }) .subscribeOn(Schedulers.io())//  现在请求会新建一个线程
                .observeOn(AndroidSchedulers.mainThread())// 切换到主线程更新UI
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) { //处理事件之前会调用
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: "+value);// 下一个，其实每个事件的处理结果
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {  // 事件完成

                Log.d(TAG, "onComplete: ");
            }
        });
    }

    /**
     * 获取网络数据，通过同步形式
     * @return
     */
    private Response<ResponseBody> initRetrofit(){
        Response<ResponseBody> response=null;
        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl("http://www.qubaobei.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IFoodService iFoodService = retrofit.create(IFoodService.class);
        Call<ResponseBody> call = iFoodService.getFoodList();
        try {
             response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }
}
