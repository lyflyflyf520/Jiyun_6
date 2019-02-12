package com.example.loginmvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginmvp.contract.LoginContract;
import com.example.loginmvp.presenter.LoginPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        LoginContract.View {

    /**
     * 登录
     */
    private Button mLogin;
    /**
     * Hello World!
     */
    private TextView mResult;
    private LoginPresenter loginPresenter = new LoginPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login:

                loginPresenter.getLoginData();
                break;
        }
    }

    @Override
    public void loginJump(String result) {

        mResult.setText(result);
    }

    @Override
    public void loginError(String result) {
        mResult.setText(result);
    }
}
