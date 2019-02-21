package com.example.monthdemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.monthdemo.ItemBean;
import com.example.monthdemo.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context context;
    private List<ItemBean.ResultBean.DataBean>  itemBeans = new ArrayList<>();


    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root  = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,null);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemBean.ResultBean.DataBean itemBean = itemBeans.get(position);
        holder.textView.setText(itemBean.getTitle());
//        holder.img.setText(itemBean.getTitle());
        // 加载图片
        loadImg(itemBean.getThumbnail_pic_s(),holder.img);


    }

    @Override
    public int getItemCount() {
        return itemBeans.size();
    }

    public void updateDate(List<ItemBean.ResultBean.DataBean> itemBeans) {

        this.itemBeans = itemBeans;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.img);
            textView= itemView.findViewById(R.id.title);
        }
    }


    private void loadImg(String url, final ImageView img){

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte [] bytes  = response.body().bytes();

                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length); // 把图片的字节长度 从0 到末尾全部放入图片工厂类，生成bitmap

                // 切换线程，给组件赋值

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img.setImageBitmap(bitmap);
                    }
                });



            }
        });
    }

}
