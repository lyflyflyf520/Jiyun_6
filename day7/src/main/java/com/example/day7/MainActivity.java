package com.example.day7;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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


        retrofitRxJava();


    }

    @SuppressLint("CheckResult")
    private void retrofitRxJava() {

        Rx2AndroidNetworking.get("http://yun918.cn/study/public/index.php/newchannel")
                .build()

                .getObjectObservable(Channel.class)
                .flatMap(new Function<Channel, ObservableSource<SubBean>>() {
                    @Override
                    public ObservableSource<SubBean> apply(Channel channel) throws Exception {
                        return Rx2AndroidNetworking.get("http://toutiao-ali.juheapi.com/toutiao/index")
                                .addHeaders("Authorization", "APPCODE db33b75c89524a56ac94d6519e106a59")
                                .addQueryParameter("type",channel.getChannels().get(0).getChannel())
                                .build()
                                .getObjectObservable(SubBean.class)
                                ;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<SubBean>() {
                    @Override
                    public void accept(SubBean channel) throws Exception {

                        Log.d(TAG, "accept: chan=" + channel.getResult().getData().size());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: e=" + throwable.getMessage());
                    }
                });


    }


    /**
     * 获取网络数据，通过同步形式
     *
     * @return
     */
    private Response<ResponseBody> initRetrofit() {
        Response<ResponseBody> response = null;
        Retrofit retrofit = new Retrofit.Builder()
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
        return response;
    }
}
