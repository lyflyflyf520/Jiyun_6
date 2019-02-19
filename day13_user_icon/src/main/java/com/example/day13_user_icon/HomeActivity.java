package com.example.day13_user_icon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.day13_user_icon.service.LoginService;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    String uId;
    String result;
    private Button mClick;
    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
//        intent.putExtra("uid",dataBean.getUid());
//        intent.putExtra("result",dataBean.toString());

        Intent intent = getIntent();
        if (intent != null) {

            uId = intent.getStringExtra("uid");
            result = intent.getStringExtra("result");
        }


    }

    private static final String TAG = "HomeActivity";
    private void uploadUserIcon(String uid) {

        //1.file
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "aa.jpg";
        File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "exists: " + true);
        }
//        2. file-->RequestBody
        RequestBody requestBody  =  RequestBody.create(MediaType.parse("image/jpg"),file);

        // 创建多媒体 请求对象
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

        // 创建 post 普通传参  文本类型  key= img
        RequestBody  keyParams = RequestBody.create(MediaType.parse("text/plain"),"img");

        // 创建 post 普通传参  文本类型  uid=728
        RequestBody  uidParams = RequestBody.create(MediaType.parse("text/plain"),uid);


        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(LoginService.uploadHeader)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService loginService = retrofit.create(LoginService.class);
        Observable<ResponseBody>  observable = loginService.uploadHeader(uidParams,keyParams, multipartBody);

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ResponseBody value) {

                        try {
                            String result = value.string();
                            Log.d(TAG, "onNext: "+ result);

                            JSONObject jsonObject = new JSONObject(result);
                            jsonObject = jsonObject.getJSONObject("data");

                            String imgUrl = jsonObject.getString("url");

                            Glide.with(HomeActivity.this).load(imgUrl).into(mImg);
//                            {"code":200,"res":"上传文件成功","data":{"url":"http://yun918.cn/study/public/uploadfiles/123/944365-ee747d1e331ed5a4.png"}}

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ;
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mImg = (ImageView) findViewById(R.id.img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:

                uploadUserIcon(uId);
                break;
        }
    }


}
