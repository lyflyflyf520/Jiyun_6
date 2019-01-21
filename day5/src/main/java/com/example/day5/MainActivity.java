package com.example.day5;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.day5.dao.DaoMaster;
import com.example.day5.dao.DaoSession;
import com.example.day5.dao.UserDao;

import java.io.IOException;
import java.util.List;
import java.util.Random;


/**
 * 实现retrofit 请求数据
 * 通过两种 get post
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    Button insertBtn;
    Button deleteBtn;
    Button updateBtn;
    Button queryBtn;
    TextView resultTv;
    EditText deleteEdit;

    SQLiteDatabase db;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

    }

    private void initView() {

//        baseData = findViewById(R.id.base_data);
        insertBtn = findViewById(R.id.insert);
        deleteBtn = findViewById(R.id.delete);
        updateBtn = findViewById(R.id.update);
        queryBtn = findViewById(R.id.query);

        resultTv = findViewById(R.id.result);
        deleteEdit = findViewById(R.id.edittext_delete);

        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
    }
    public DaoSession getDaoSession(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this,"hh.db");

        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        return  daoMaster.newSession();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.insert:
                insertData();
                break;
            case R.id.delete:
                deleteData();
                break;
            case R.id.update:
                updateData();
                break;
            case R.id.query:
                String input = deleteEdit.getText().toString().trim();
                User user = queryData(input);

                resultTv.setText(user.getName()+"="+user.getAge());
                break;
        }
    }


    public void insertData() {
        String input = deleteEdit.getText().toString().trim();

        if (input == null) {
            return;
        }
        User user = new User();
        String params[] ;
        if (input.contains(",")){
            params = input.split(",");
            user.setName(params[0]);
            user.setAge(params[1]);
        }else{
            user.setName(input);
        }
      DaoSession daoSession=   getDaoSession();

      daoSession.getUserDao().insert(user);

    }

    public void deleteData() {
        String input = deleteEdit.getText().toString().trim();

        DaoSession daoSession=   getDaoSession();

        User user = queryData(input);
        daoSession.getUserDao().delete(user);
    }

    public void updateData() {
        String input = deleteEdit.getText().toString().trim();

        DaoSession daoSession=   getDaoSession();
        User user = queryData(input);

        user.setAge("999");
        daoSession.getUserDao().update(user);

    }

    /**
     * loadAll()：查询所有记录
     * load(Long key)：根据主键查询一条记录
     * queryBuilder().list()：返回：List<User>列表
     * queryBuilder().where(UserDao.Properties.Name.eq("")).list()：返回：List<User>列表
     * queryRaw(String where,String selectionArg)：返回：List<User>列表
     */
    public User queryData(String name) {
        DaoSession daoSession=   getDaoSession();

        List<User> list = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Name.eq(name)).list();
        return list.size()>0? list.get(0):null;
    }


}
