package com.example.annotationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;

/**
 * 用处;
 * 通过注解 替换 view activity 布局声明
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.click)
    Button clickBtn;

    @ViewById(R.id.result)
    TextView resultTv;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


    }

    @Click(R.id.click)
    public void btnClick(){

        resultTv.setText("hhhhhhhh");
        Log.d(TAG, "btnClick: ");
    }

    @LongClick(R.id.click)
    public void OnLongClick(){
        resultTv.setText("fffffffffffff");
        Log.d(TAG, "OnLongClick: ");
    }

}
