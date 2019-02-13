package com.example.mvpdbdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mvpdbdemo.contract.DbContract;
import com.example.mvpdbdemo.presenter.DbPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, DbContract.View {

    /**
     * 输入id,name,age
     */
    private EditText mInputInsert;
    /**
     * 插入
     */
    private Button mInsertBtn;
    private LinearLayout mInsertLayout;
    /**
     * 输入id
     */
    private EditText mDeleteInsert;
    /**
     * 删除
     */
    private Button mDeleteBtn;
    private LinearLayout mDeleteLayout;
    /**
     * 查询所有
     */
    private Button mQueryAll;
    /**
     * 查询单个
     */
    private Button mQueryOne;
    private LinearLayout mQueryLayout;
    /**
     * Hello World!
     */
    private TextView mResult;
    DbPresenter dbPresenter = new DbPresenter(this);
    /**
     * 输入id
     */
    private EditText mQuerySingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }


    private void initView() {
        mInputInsert = (EditText) findViewById(R.id.input_insert);
        mInsertBtn = (Button) findViewById(R.id.insert_btn);
        mInsertBtn.setOnClickListener(this);
        mInsertLayout = (LinearLayout) findViewById(R.id.insert_layout);
        mDeleteInsert = (EditText) findViewById(R.id.delete_insert);
        mDeleteBtn = (Button) findViewById(R.id.delete_btn);
        mDeleteBtn.setOnClickListener(this);
        mDeleteLayout = (LinearLayout) findViewById(R.id.delete_layout);
        mQueryAll = (Button) findViewById(R.id.query_all);
        mQueryAll.setOnClickListener(this);
        mQueryOne = (Button) findViewById(R.id.query_one);
        mQueryOne.setOnClickListener(this);
        mQueryLayout = (LinearLayout) findViewById(R.id.query_layout);
        mResult = (TextView) findViewById(R.id.result);
        mQuerySingle = (EditText) findViewById(R.id.query_single);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.insert_btn:

                dbPresenter.insert(getInsertContent());
                break;
            case R.id.delete_btn:
                String id = mDeleteInsert.getText().toString().trim();

                if (!TextUtils.isEmpty(id)) {
                    dbPresenter.delete(id);
                }
                break;
            case R.id.query_all:

                dbPresenter.queryall();

                break;
            case R.id.query_one:

                id = mQuerySingle.getText().toString().trim();

                if (!TextUtils.isEmpty(id)) {
                    dbPresenter.queryone(id);
                }

                break;
        }
    }

    /**
     * 获取输入的内容，根据"," 分割，组装成对象itembean
     *
     * @return
     */
    public ItemBean getInsertContent() {

        ItemBean itemBean = null;
        String insert = mInputInsert.getText().toString().trim();

        if (!TextUtils.isEmpty(insert)) {

            itemBean = new ItemBean();
            String[] result = new String[3];
            if (insert.contains(",")) {
                result = insert.split(","); // id,name,age
            }
            itemBean.setMId(result[0]);
            itemBean.setName(result[1]);
            itemBean.setAge(result[2]);

        }

        return itemBean;
    }


    @Override
    public void updateInsert() {


    }

    @Override
    public void updateDelete() {

    }

    private static final String TAG = "MainActivity";
    @Override
    public void updateQueryall(List<ItemBean> itemBeans) {

        StringBuilder sb= new StringBuilder();

        for (ItemBean itemBean:itemBeans){

            Log.d(TAG, "updateQueryall: item="+itemBean.toString());

            sb.append(itemBean.toString()+"\n");

        }
        mResult.setText(sb.toString());
    }

    @Override
    public void updateQueryone(ItemBean itemBean) {
        Log.d(TAG, "updateQueryall: item="+itemBean.toString());

        mResult.setText(itemBean.toString());
    }
}
