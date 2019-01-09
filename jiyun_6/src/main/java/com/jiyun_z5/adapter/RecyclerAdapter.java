package com.jiyun_z5.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiyun_z5.R;
import com.jiyun_z5.bean.BannerItem;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    private List<BannerItem> bannerItems;

    private Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<BannerItem> bannerItems) {
        this.bannerItems = bannerItems;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, null);

        return new RecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       BannerItem ban = bannerItems.get(position);
        holder.textView.setText(ban.getTitle());

        getImageBitmap(holder.imageView,ban.getImagePath());

    }


    @Override
    public int getItemCount() {
        return bannerItems == null ? 0 : bannerItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            textView = itemView.findViewById(R.id.textview);
        }
    }


    public void getImageBitmap(final ImageView imgView,String imgPath) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(imgPath).build();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    Log.d(TAG, "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) {
                    Bitmap bitmap = null;
                    try {
                        byte[] bytes = response.body().bytes();
                        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Log.d(TAG, "onResponse: UI="+(Looper.getMainLooper()==Looper.myLooper())+"--bitmap="+bitmap);


                        final Bitmap finalBitmap = bitmap;
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                imgView.setImageBitmap(finalBitmap);
                                Log.d(TAG, "run: "+(Looper.getMainLooper()==Looper.myLooper()));
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
