package com.example.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * map
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rxjavaTest();
        rxjavaNet();


    }

    private void rxjavaNet() {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("33");
                subscriber.onCompleted();
            }
        })
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.valueOf(s);
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer + "";
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(String s) {

                        Log.d(TAG, "onNext: ");
                    }
                });

    }

    private void rxjavaTest() {

        /** 观察者*/
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {

                Log.d(TAG, "onNext: " + s);
            }
        };

        /** 被观察者 */
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("脱衣服");
                subscriber.onNext("睡觉");
                subscriber.onCompleted();
            }
        });

        observable.subscribe(observer);

    }



}
