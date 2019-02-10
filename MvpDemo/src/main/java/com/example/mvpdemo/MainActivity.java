package com.example.mvpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mvpdemo.contract.LoginContract;
import com.example.mvpdemo.presenter.LoginPresenter;

public class MainActivity extends AppCompatActivity implements LoginContract.View {
    private LoginPresenter loginPresenter;

    TextView resultTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMvp();

        resultTv = findViewById(R.id.result);
        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.login("username", "123");
            }
        });
    }

    private void initMvp() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void updateUI(String result) {

        resultTv.setText(result);
    }

    @Override
    public void faildUI(String result) {
        resultTv.setText(result);
    }
}
