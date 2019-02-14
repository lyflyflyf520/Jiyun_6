package com.example.eventbusdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.click)
    Button button;
    @BindView(R.id.textview)
    TextView result;


    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);// 绑定当前context-activity、service、application




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");

//                EventBus.getDefault().post(new MsgEvent("333"));
                EventBus.getDefault().postSticky(new MsgEvent("123-456"));

                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) // 声明eventbus 接受订阅的事件，发生在主线程
    public void onMessageEvent(MsgEvent event){


        result.setText(event.getMsg());

//        EventBus.getDefault().postSticky(new MsgEvent());
//
//        startActivity(new Intent(MainActivity.this,Main2Activity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

//        EventBus.getDefault().unregister(this);
    }
}
