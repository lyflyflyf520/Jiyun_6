package com.example.day10_eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * eventbus
 * <p>
 * 1.注册
 * 2.取消注册
 * 3.订阅事件的接收
 * 4.发送事件
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * click
     */
    private Button mClick;
    /**
     * Hello World!
     */
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        //注册
//        EventBus.getDefault().register(this);
    }

    /**
     * 接收 订阅的事件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent event) {

        mContent.setText(event.getContent());

    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消注册
//        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mContent = (TextView) findViewById(R.id.content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:

                EventBus.getDefault().postSticky(new MsgEvent(111,"this is eventbus sticky event"));

                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                break;
        }
    }
}
