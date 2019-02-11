package com.example.mvp_34;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp_34.contract.IFoodListContract;
import com.example.mvp_34.presenter.IFoodListPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IFoodListContract.View {

    private RecyclerView mRecycler;
    private MyAdapter adapter;
    /**
     * 收藏列表
     */
    private TextView mCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initMvp();
    }

    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new MyAdapter(this);
        mRecycler.setAdapter(adapter);

        adapter.setOnLongClickListener(new MyAdapter.OnLongClickListener() {
            @Override
            public void onLongItem(int position) {

                DbUtil.insert(MainActivity.this, adapter.getDataBean(position));
                Toast.makeText(MainActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }
        });
        mCollect = (TextView) findViewById(R.id.collect);
        mCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CollectActivity.class));
            }
        });
    }

    public void initMvp() {

        IFoodListPresenter iFoodListPresenter = new IFoodListPresenter(this);
        iFoodListPresenter.getListData();
    }


    @Override
    public void updateListData(List<DataBean> data) {
        adapter.updateData(data);
        Toast.makeText(this, "获取成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateListFaild(String value) {
        Toast.makeText(this, "获取失败", Toast.LENGTH_SHORT).show();
    }
}
