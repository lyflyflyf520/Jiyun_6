package com.example.day9;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.day9.contract.FoodContract;
import com.example.day9.presenter.FoodPresenter;

import java.util.List;

/**
 * 从数据库 获取数据展示
 */
public class Main2Activity extends AppCompatActivity implements FoodContract.View {

    private RecyclerView mRecycler;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();

        initMvp();
    }

    private void initMvp() {

        FoodPresenter foodPresenter = new FoodPresenter(this);

        foodPresenter.getDbData();
    }

    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration( new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        myAdapter = new MyAdapter(this);

        mRecycler.setAdapter(myAdapter);
    }

    @Override
    public void updateFoodList(List< DataBean> dataBeanList) {

    }

    @Override
    public void updateFaild(String errMsg) {

    }

    @Override
    public void updateDbData(List< DataBean> dataBeanList) {
        myAdapter.updateData(dataBeanList);
    }
}
