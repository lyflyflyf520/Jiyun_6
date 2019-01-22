package com.example.day_05;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText inputContent;
    private TextView result;
    private Button insert;
    private Button delete;
    private Button update;
    private Button query;

    String tableName="user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        inputContent = (EditText) findViewById(R.id.input_content);
        result = (TextView) findViewById(R.id.result);
        insert = (Button) findViewById(R.id.insert);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);
        query = (Button) findViewById(R.id.query);


        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        query.setOnClickListener(this);

    }


    private void insertData(){

        // 获取sqliteopenhelper对象
     MySqliteOpenHelper mySqliteOpenHelper=   MyApplication.getSqliterHelper();
     // 获取数据库对象
     SQLiteDatabase  sqLiteDatabase = mySqliteOpenHelper.getWritableDatabase();

        String input = inputContent.getText().toString();
        ContentValues contentValues = new ContentValues();
        String [] params=null;   // lisi,24
        if (input.contains(",")){  //根据输入对象拆分内容
            params = input.split(",");
            contentValues.put("name",params[0]);
            contentValues.put("age",params[1]);
        }else{
            contentValues.put("name",input);
        }

        sqLiteDatabase.insert(tableName,null,contentValues);


    }
    private void deleteData(){

    }
    private void updateData(){

    }
    private void queryData(){

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
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
}
