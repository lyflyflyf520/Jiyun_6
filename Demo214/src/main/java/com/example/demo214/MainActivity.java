package com.example.demo214;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demo214.service.INewsSerivce;
import com.example.demo214.utils.DbUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecycler;

    MyAdapter myAdapter;
    /**
     * 收藏列表
     */
    private Button mCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private static final String TAG = "MainActivity";

    private void initData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(INewsSerivce.newUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        INewsSerivce iNewsSerivce = retrofit.create(INewsSerivce.class);

        Observable<ResultBean> observable = iNewsSerivce.getNewsData();

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean value) {

                        myAdapter.updateData(value.getT1348647909107());

                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: " + e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        myAdapter = new MyAdapter(this);
        mRecycler.setAdapter(myAdapter);
        mCollect = (Button) findViewById(R.id.collect);
        mCollect.setOnClickListener(this);

        myAdapter.setCallBack(new MyAdapter.CallBack() {
            @Override
            public void LongClick(T1348647909107Bean dataBean) {

                //
                DbUtil dbUtil = new DbUtil();
                dbUtil.insert(dataBean);

                Toast.makeText(MainActivity.this, "插入完事", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.collect:
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                break;
        }
    }
}
