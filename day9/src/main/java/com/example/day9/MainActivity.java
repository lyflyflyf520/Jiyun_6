package com.example.day9;

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

import com.example.day9.contract.FoodContract;
import com.example.day9.presenter.FoodPresenter;

import java.util.List;

/**
 * 实现 一个网络数据的列表，包含图片，文字
 * 使用mvp rxjava retrofit
 */
public class MainActivity extends AppCompatActivity implements FoodContract.View, View.OnClickListener {

    private RecyclerView mRecycler;
    MyAdapter myAdapter;

    private static final String TAG = "MainActivity";
    FoodPresenter foodPresenter;
    /**
     * 收藏列表
     */
    private Button mCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initMvp();
    }

    private void initMvp() {

        foodPresenter = new FoodPresenter(this);
        foodPresenter.getFoodList("1");

    }

    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        myAdapter = new MyAdapter(this);

        mRecycler.setAdapter(myAdapter);

        myAdapter.setCallBack(new MyAdapter.CallBack() {
            @Override
            public void LongClick(DataBean dataBean) {
                //  保存到数据库
                foodPresenter.saveItemData(dataBean);

                Toast.makeText(MainActivity.this, "保存成功="+dataBean.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        mCollection = (Button) findViewById(R.id.collection);
        mCollection.setOnClickListener(this);
    }


    @Override
    public void updateFoodList(List<DataBean> dataBeanList) {

        myAdapter.updateData(dataBeanList);

    }

    @Override
    public void updateFaild(String errMsg) {

        Log.d(TAG, "updateFaild: error=" + errMsg);
    }

    @Override
    public void updateDbData(List<DataBean> dataBeanList) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.collection:

                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                break;
        }
    }
}
