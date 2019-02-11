package com.example.mvp_34;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;


public class CollectActivity extends Activity {
    private RecyclerView mRecycler;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_activity);
        initView();
        initData();
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
                DataBean dataBean = adapter.getDataBean(position);
                DbUtil.delete(CollectActivity.this,dataBean);
                adapter.deleteDataBean(dataBean);
                Toast.makeText(CollectActivity.this, "delete success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData(){
        List<DataBean> dataBeans = DbUtil.query(this);

        adapter.updateData(dataBeans);
    }
}
