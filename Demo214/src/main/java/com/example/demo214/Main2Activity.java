package com.example.demo214;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.example.demo214.utils.DbUtil;

import java.util.List;


public class Main2Activity extends Activity {
    private RecyclerView mRecycler;
    private MyAdapter myAdapter;

    DbUtil dbUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);

        dbUtil = new DbUtil();
        initView();
        initData();
    }

    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        myAdapter = new MyAdapter(this);
        mRecycler.setAdapter(myAdapter);

        myAdapter.setCallBack(new MyAdapter.CallBack() {
            @Override
            public void LongClick(T1348647909107Bean dataBean) {

                dbUtil.deleteOne(dataBean);

                myAdapter.deleteList(dataBean);
                Toast.makeText(Main2Activity.this, "delete is success!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initData(){

        List<T1348647909107Bean>  t1348647909107BeanList = dbUtil.getDbAll();
        if (t1348647909107BeanList!=null&&!t1348647909107BeanList.isEmpty()){
            myAdapter.updateData(t1348647909107BeanList);
        }

    }
}
