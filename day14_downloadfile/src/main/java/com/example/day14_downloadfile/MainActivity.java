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
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 下载
     */
    private Button mClick;
    private File file;
    private ProgressBar mProbarDownload;
    /**
     * okhttp下载
     */
    private Button mOkClick;

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
     *
     * @param fileUrl 服务器文件的地址
     */
    private void doHttpTask(String fileUrl) {
        try {
            String name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            File file = new File(DEFAULT_TARGET_FOLDER_PATH + name);

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            HttpURLConnection connection = getConnection(fileUrl);
            InputStream inputStream = connection.getInputStream();

            byte[] bytes = new byte[1024];
            int hasRead;
            int readCount = 0;// 当前已读的总文件大小
            while ((hasRead = inputStream.read(bytes)) > 0) {
                fileOutputStream.write(bytes, 0, hasRead);

                readCount += hasRead;
                int progress = (int) ((readCount * 100) / connection.getContentLength());  //30%
                mProbarDownload.setProgress(progress);//
                Log.d(TAG, "doHttpTask: 当次写入=--" + hasRead + "--progress=" + progress);
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
     *
     * @param fileUrl
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getConnection(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10 * 1000);
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
        mProbarDownload = (ProgressBar) findViewById(R.id.probar_download);
        mProbarDownload.setMax(100);


        mOkClick = (Button) findViewById(R.id.ok_click);
        mOkClick.setOnClickListener(this);
    }

    //UnknowApp-1.0.apk
    private String apk_url = "http://yun918.cn/study/public/res/UnknowApp-1.0.apk";
//    private String apk_url = "https://ws1.sinaimg.cn/large/0065oQSqgy1fze94uew3jj30qo10cdka.jpg";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        doHttpTask(apk_url);
                    }
                }.start();


                break;
            case R.id.ok_click:

                downLoadOk(apk_url);
                break;
        }
    }

    private void downLoadOk(String fileUrl) {


        String name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        final File file = new File(DEFAULT_TARGET_FOLDER_PATH + name);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apk_url)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d(TAG, "onFailure: e="+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;

                try {
                      inputStream = response.body().byteStream();

                    // 输出流 绑定了本地文件，等于下载数据到本地文件
                      fileOutputStream = new FileOutputStream(file);

                    byte[] bytes = new byte[1024];
                    int readLength=0;
                    int isReadedCount =0;
                    int fileLength = (int) response.body().contentLength();

                    while((readLength=inputStream.read(bytes,0,bytes.length))!=-1){

                        fileOutputStream.write(bytes,0,readLength);

                        isReadedCount += readLength;

                        int progress = (isReadedCount*100)/fileLength;

                        mProbarDownload.setProgress(progress);
                        Log.d(TAG, "onResponse: read_length="+readLength);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    inputStream.close();
                    fileOutputStream.close();

                }
            }
        });

    }
}
