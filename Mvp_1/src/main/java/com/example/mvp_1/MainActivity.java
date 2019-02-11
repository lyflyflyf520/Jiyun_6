package com.example.mvp_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvp_1.contract.IUpdateUIContract;
import com.example.mvp_1.presenter.IUpdateUIPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
,IUpdateUIContract.View{

    private Button click;
    private TextView resultTv;

    IUpdateUIContract.Presenter presenter;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMvp();

        click = (Button) findViewById(R.id.click);
        resultTv = (TextView) findViewById(R.id.result);

        click.setOnClickListener(this);
    }

    private void initMvp(){
        presenter =new IUpdateUIPresenter(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.click:
                presenter.updateUI();
                break;

        }
    }

    @Override
    public void updateUISuccess(String result) {
        resultTv.setText(result);
        Log.d(TAG, "updateUISuccess: ");
    }

    @Override
    public void updateUIFailed(String result) {
        resultTv.setText(result);
        Log.d(TAG, "updateUIFailed: ");
    }
}
