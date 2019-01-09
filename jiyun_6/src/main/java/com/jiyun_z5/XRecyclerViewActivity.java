package com.jiyun_z5;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiyun_z5.adapter.RecyclerAdapter;
import com.jiyun_z5.bean.BannerItem;
import com.jiyun_z5.utils.CacheUtils;
import com.jiyun_z5.utils.Constant;
import com.jiyun_z5.utils.DiskLruCacheUtils;
import com.jiyun_z5.utils.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.jiyun_z5.utils.Constant.food_url;
import static com.jiyun_z5.utils.Constant.home_list_url;
import static com.jiyun_z5.utils.Constant.img_url;

public class XRecyclerViewActivity extends AppCompatActivity {

    public static final int REQUEST_IMG = 99;
    public static final int REQUEST_TEXT = 199;
    private ImageView imageView;
    private XRecyclerView xRecyclerView;
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == REQUEST_IMG) {

                Bitmap bitmap = (Bitmap) msg.obj;
                if (diskLruCacheUtils != null) {
                    diskLruCacheUtils.saveBitmap2Disk(img_url);
                }
                imageView.setImageBitmap(bitmap);
            } else if (msg.what == REQUEST_TEXT) {
                try {

                    String result = (String) msg.obj;
                    if (TextUtils.isEmpty(CacheUtils.getJSONData(Constant.home_list_url))) {
                        CacheUtils.saveJSONData(Constant.home_list_url, result);
                    } else {
                        result = CacheUtils.getJSONData(Constant.home_list_url);
                    }
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for (int x = 0; x < jsonArray.length(); x++) {
                        JSONObject obj = jsonArray.getJSONObject(x);
                        BannerItem bannerItem = new BannerItem();
                        bannerItem.setTitle(obj.optString("title"));
                        bannerItem.setImagePath(obj.optString("imagePath"));
                        bannerItemList.add(bannerItem);
                    }
                    recyclerAdapter.updateData(bannerItemList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };
    private static final String TAG = "MainActivity";
    private List<BannerItem> bannerItemList = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    ImageLoader imgLoader;
    DiskLruCacheUtils diskLruCacheUtils ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLoader = new ImageLoader(this.getApplicationContext());

        diskLruCacheUtils = new DiskLruCacheUtils(this);

        imageView = new ImageView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        imageView.setLayoutParams(params);

        xRecyclerView = findViewById(R.id.xrecyclerview);

        //添加一个布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        //添加头部布局
        xRecyclerView.addHeaderView(imageView);

        //添加Android自带的分割线
        xRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerAdapter = new RecyclerAdapter(this);
        xRecyclerView.setAdapter(recyclerAdapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: ");
                xRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {

                Log.d(TAG, "onLoadMore: ");
                xRecyclerView.loadMoreComplete();
            }
        });
//        loadImgData();


    }


    @Override
    protected void onResume() {
        super.onResume();

        if (diskLruCacheUtils.getBitmap2key(img_url) != null) {
            imageView.setImageBitmap(diskLruCacheUtils.getBitmap2key(img_url));
        } else {

            loadImgByOkHttp();


        }

        loadDataByOkHttp();


    }

    public void loadDataByOkHttp() {
        try {
            //post方式提交的数据
//            FormBody formBody = new FormBody.Builder()
//                    .add("name", "android基础")
//                    .add("price", "50")
//                    .build();

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
//                    .post(formBody)
                    .url(home_list_url)
                    .build();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();

                    Message message = Message.obtain();
                    message.obj = result;
                    message.what = REQUEST_TEXT;
                    handler.sendMessage(message);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadImgByOkHttp() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    //表示一个发起网络请求 对网络请求做了一个封装  可以声明请求的地址和带了一些请求参数和同步信息
                    .url(img_url)
                    .build();
            Call call = okHttpClient.newCall(request);
//        通过newCall方法得到 Call对象。 Call对象里面的execute() 表示发起一个同步请求，同时返回的结果是Response对象 (服务器反应的结果)

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) {
                    InputStream inputStream = response.body().byteStream();

                    Bitmap bitmap = null;

                    bitmap = BitmapFactory.decodeStream(inputStream);

                    //下面这是把图片携带在Message里面
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    message.what = REQUEST_IMG;
                    handler.sendMessage(message);
                }
            });

//            System.out.println(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过httpUrlConnection获取图片对象
     */
    public void loadImgByHttpUrlConnection() {

        new Thread() {
            public void run() {
                try {
                    URL url = new URL(img_url);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 设置连接超时时间
//                    connection.setConnectTimeout(5000);
//                    // 设置读取超时时间
//                    connection.setReadTimeout(5000);
//                    // 设置请求参数，即具体的 HTTP 方法  POST  http://baidu.com?key=value
//                    connection.setRequestMethod("GET");
//                    // 添加 HTTP HEAD 中的一些参数
//                    connection.setRequestProperty("Connection", "Keep-Alive");
//                    // 设置是否向 httpUrlConnection 输出，
//                    connection.setDoOutput(true);
//                    // 设置是否从 httpUrlConnection 读入，默认情况下是true;
//                    connection.setDoInput(true);

                    InputStream inputStream = connection.getInputStream();

//                    把输出流 转换为 字符串对象
                    //用bitmap取出了这个流里面的图片
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    //下面这是把图片携带在Message里面
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    message.what = REQUEST_IMG;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 获取文本信息
     */
    public void loadStrByHttpUrlconnection(){
        try {
            URL url = new URL(food_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream ));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            String reponse = sb.toString();

            Log.d(TAG, "loadStrByHttpUrlconnection: "+reponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
