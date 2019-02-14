package com.example.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.post_sticky)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);

       EventBus.getDefault().register(this);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MsgEvent msgEvent) {
        textView.setText(msgEvent.getMsg());
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);

    }
}
