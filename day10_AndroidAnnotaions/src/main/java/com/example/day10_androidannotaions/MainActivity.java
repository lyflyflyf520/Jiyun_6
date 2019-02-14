package com.example.day10_androidannotaions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 *  通过注解的形式声明组件
 *
 *  activtity fragment
 *  view
 *
 *  1.导入依赖
 *  2.声明注解 @EActivity(R.layout.activity_main)
 *  3.改写清单文件里activity的后缀，“_”
 *  4.编译
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.click)
    Button click;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);



    }

    @Click(R.id.click)
    public void onClick(){
        Log.d(TAG, "onClick: this is week four");
    }


}
