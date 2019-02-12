package com.example.day8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.day8.contract.IUpdateListenerContract;
import com.example.day8.presenter.IUpdateListenerPresenter;

public class MainActivity extends AppCompatActivity implements IUpdateListenerContract.View,
        View.OnClickListener {

    private static final String TAG = "MainActivity";
    /**
     * 点击刷新
     */
    private Button mClick;
    /**
     * Hello World!
     */
    private TextView mResult;
    private IUpdateListenerPresenter iUpdateListenerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initMvp();
    }

    private void initMvp(){
         iUpdateListenerPresenter = new IUpdateListenerPresenter(this);

    }
    @Override
    public void updateUISuccess(String result) {

        mResult.setText(result);
        Log.d(TAG, "updateUISuccess: "+result);
    }

    @Override
    public void updateUIFailed(String result) {
        mResult.setText(result);
        Log.d(TAG, "updateUIFailed: "+result);
    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:
                iUpdateListenerPresenter.updateUIByRadom();
                break;
        }
    }
}
