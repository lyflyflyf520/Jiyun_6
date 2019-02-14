package com.example.demo214;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    List<T1348647909107Bean> dataBeanList = new ArrayList<>();

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<T1348647909107Bean> dataBeanList) {

        if (this.dataBeanList!=null){

            this.dataBeanList.addAll(dataBeanList);
            notifyDataSetChanged();
        }


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.recycler_item, null);
        return new MyViewHolder(root);
    }

    private static final String TAG = "MyAdapter";
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        final T1348647909107Bean dataBean = dataBeanList.get(position);

        myViewHolder.mTitle.setText(dataBean.getTitle());
        Glide.with(context).load(dataBean.getImgsrc())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.d(TAG, "onException: e="+e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(myViewHolder.mImg);

        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (callBack!=null){
                    callBack.LongClick(  dataBean);
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public void deleteList(T1348647909107Bean dataBean) {

        if (dataBeanList!=null&&dataBeanList.contains(dataBean)){
            dataBeanList.remove(dataBean);
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        TextView mTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mImg = (ImageView) itemView.findViewById(R.id.img);
            this.mTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }


    CallBack callBack;

    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

    public interface CallBack{

        void LongClick(T1348647909107Bean dataBean);
    }

}
