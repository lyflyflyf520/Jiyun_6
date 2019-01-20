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
    EditText updateEdit;

    SQLiteDatabase db;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MySQLiteOpenHelper(getApplicationContext(), "mydb", null, 1).getWritableDatabase();
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
        updateEdit = findViewById(R.id.edittext_update);

        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
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
                queryData();
                break;
        }
    }


    public void insertData() {


        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "rrr");
        contentValues.put("address", "fffff");
        //插入
        db.insert("user", null, contentValues);
        resultTv.setText("当前数据为：" + queryData());
    }

    public void deleteData() {

        String input = deleteEdit.getText().toString().trim();

        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "输入名称", Toast.LENGTH_SHORT).show();
        }
        //删除
        db.delete("user", "name = ?", new String[]{input});

        resultTv.setText("当前数据为：" + queryData());
    }

    public void updateData() {
        String input = updateEdit.getText().toString().trim();

        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "输入名称", Toast.LENGTH_SHORT).show();
            input = "";
        }

        //更新
        ContentValues cv2 = new ContentValues();
        cv2.put("name", "ddd");
        db.update("user", cv2, "name=?", new String[]{input});
//        Log.v("mytab","-->修改完成！");
//        Log.v("mytab","当前数据为："+getAllData());
        resultTv.setText("当前数据为：" + queryData());

    }

    /**
     * loadAll()：查询所有记录
     * load(Long key)：根据主键查询一条记录
     * queryBuilder().list()：返回：List<User>列表
     * queryBuilder().where(UserDao.Properties.Name.eq("")).list()：返回：List<User>列表
     * queryRaw(String where,String selectionArg)：返回：List<User>列表
     */
    public String queryData() {
        Cursor cursor = db.query("user", new String[]{}, "", new String[]{}, "", "", "");

        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String myname = cursor.getString(cursor.getColumnIndex("name"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            stringBuffer.append("名字->" + myname + "--地址=" + address);
        }

        resultTv.setText(stringBuffer.toString());

        return stringBuffer.toString();
    }


}
