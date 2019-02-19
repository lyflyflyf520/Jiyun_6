package com.example.mvc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        IUpdateListener, View.OnClickListener {

    /**
     * 点击刷新
     */
    private Button mClick;
    /**
     * Hello World!
     */
    private TextView mResult;
    IModelmpl iModelmpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initMvc();
    }
    public void initMvc(){
        iModelmpl = new IModelmpl();
    }
    @Override
    public void updateUI(String result) {

        mResult.setText(result);
    }

    @Override
    public void updateUIFailed(String result) {
        mResult.setText(result);
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

                iModelmpl.updateUI( this);
                break;
        }
    }


}
