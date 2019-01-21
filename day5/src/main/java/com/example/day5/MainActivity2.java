package com.example.day5;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends Activity implements View.OnClickListener {
    private EditText input;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView textView;
    MySQLiteOpenHelper mySQLiteOpenHelper;
    @Override
    protected void onCreate(  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(this,"user.db",null,1);

        input = (EditText) findViewById(R.id.input);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button:
                String name = input.getText().toString();
                insertData(name);
                break;
            case R.id.button2:
                 name = input.getText().toString();
                deleteData(name);
                break;
            case R.id.button3:
                name = input.getText().toString();
                updateData(name);
                break;
            case R.id.button4:
             String result =  queryData();
             textView.setText(result);
                break;
        }
    }
    private String queryData(){
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getReadableDatabase();

       Cursor cursor =  sqLiteDatabase.query("user",new String[]{},"",new String[]{},"","","");
       StringBuffer stringBuffer = new StringBuffer();
       while(cursor.moveToNext()){
           String name = cursor.getString(cursor.getColumnIndex("name"));
           String age = cursor.getString(cursor.getColumnIndex("age"));
           stringBuffer.append(name+"-"+age+",");
       }
       return stringBuffer.toString();
    }
    private void updateData(String name){
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","xxx");
        sqLiteDatabase.update("user",contentValues,"name=?",new String[]{name});
    }

    private void insertData(String str){
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (str == null) {
            return;
        }
        String params[] ;
       if (str.contains(",")){
           params = str.split(",");
           contentValues.put("name",params[0]);
           contentValues.put("age",params[1]);
       }else{
           contentValues.put("name",str);
       }

        sqLiteDatabase.insert("user","",contentValues);
    }

    private void deleteData(String name){
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();

        sqLiteDatabase.delete("user","name=?",new String[]{name});
    }
}
