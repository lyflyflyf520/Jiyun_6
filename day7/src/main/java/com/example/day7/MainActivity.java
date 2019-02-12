package com.example.day7;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        retrofitRxJava();

//        testRxjavaAndRetrofit();
        testRxjavaRetrofit();
//        initObserable();
    }

    private void initObserable() {
        Rx2AndroidNetworking.get("http://yun918.cn/study/public/index.php/newchannel")
                .build()
                .getObjectObservable(Channel.class)//  返回被观察者  结果实体类型

                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

                // 从一个对象 转换成另外一个对象  channel  --->  string
                .map(new Function<Channel, String>() {
                    @Override
                    public String apply(Channel channel) throws Exception {
                        return channel.getChannels().get(0).getName();// 头条
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String channel) throws Exception {

                        Log.d(TAG, "accept: channel=" + channel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.d(TAG, "accept: throwable=" + throwable.getMessage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void retrofitRxJava() {

        // 处理网络请求，及返回一个被观察者
        Rx2AndroidNetworking.get("http://yun918.cn/study/public/index.php/newchannel")
                .build()
                .getObjectObservable(Channel.class)


                .flatMap(new Function<Channel, ObservableSource<SubBean>>() {
                    @Override
                    public ObservableSource<SubBean> apply(Channel channel) throws Exception {

                        return Rx2AndroidNetworking.get("http://toutiao-ali.juheapi.com/toutiao/index")
                                .addHeaders("Authorization", "APPCODE db33b75c89524a56ac94d6519e106a59")
                                .addQueryParameter("type", channel.getChannels().get(0).getChannel())//type="top"
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



    //Flowable 发射数据完成以后再接收  参考：https://www.heqiangfly.com/2017/10/14/open-source-rxjava-guide-flowable/
    private void testFlowable() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                System.out.println("发射----> 1");
                e.onNext(1);
                System.out.println("发射----> 2");
                e.onNext(2);
                System.out.println("发射----> 3");
                e.onNext(3);
                System.out.println("发射----> 完成");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER) //create方法中多了一个BackpressureStrategy类型的参数
                .subscribeOn(Schedulers.newThread())//为上下游分别指定各自的线程
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {   //onSubscribe回调的参数不是Disposable而是Subscription
                        s.request(Long.MAX_VALUE);            //注意此处，暂时先这么设置
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("接收----> " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("接收----> 完成");
                    }
                });
    }


    /**
     * rxjava retrofit 结合使用
     */
    public void testRxjavaRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.qubaobei.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        IFoodService iFoodService = retrofit.create(IFoodService.class);

        Observable<ResponseBody> observable = iFoodService.getObservale();

        observable.subscribeOn(Schedulers.io())//  在子线程订阅事件
                .observeOn(AndroidSchedulers.mainThread()) // 切换到主线程更新UI
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        try {
                            Log.d(TAG, "onNext: "+responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: ");
                    }
                });

    }
}
