package com.example.day14_downloadfile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.Manifest.permission.WRITE_SECURE_SETTINGS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 下载
     */
    private Button mClick;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    private static final String TAG = "MainActivity";
    private final String DEFAULT_TARGET_FOLDER_PATH = Environment.getExternalStorageDirectory() + File.separator;

    /**
     * 下载  文件
     * @param fileUrl  服务器文件的地址
     */
    private void doHttpTask(String  fileUrl) {
        try {

//            /sdcar/0/xx.png
            String name = fileUrl.substring(fileUrl.lastIndexOf("/")+1);

            File file = new File(DEFAULT_TARGET_FOLDER_PATH+name);

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            HttpURLConnection connection = getConnection(fileUrl);
            InputStream inputStream = connection.getInputStream();

            byte[] bytes = new byte[8*1024];
            int hasRead;
            while ((hasRead = inputStream.read(bytes)) > 0) {
                fileOutputStream.write(bytes, 0, hasRead);

                Log.d(TAG, "doHttpTask: 当次写入=--"+hasRead);
            }
            fileOutputStream.close();
            inputStream.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {


        }

    }


    /**
     * 创建connection对象，且配置
     * @param fileUrl
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getConnection(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10*1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        conn.setRequestProperty("Connection", "Keep-Alive");
//        conn.setRequestProperty("Accept-Encoding", "identity");
        return conn;
    }
    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
    }

    //UnknowApp-1.0.apk
//    private String apk_url = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
    private String apk_url = "https://ws1.sinaimg.cn/large/0065oQSqgy1fze94uew3jj30qo10cdka.jpg";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:


                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        doHttpTask(apk_url);
                    }
                }.start();


                break;
        }
    }
}
