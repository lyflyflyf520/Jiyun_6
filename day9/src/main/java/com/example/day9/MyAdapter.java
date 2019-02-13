package com.example.day9;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    List<DataBean> dataBeanList = new ArrayList<>();

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List< DataBean> dataBeanList) {

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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        final DataBean dataBean = dataBeanList.get(position);

        myViewHolder.mTitle.setText(dataBean.getTitle());
        Glide.with(context).load(dataBean.getPic()).into(myViewHolder.mImg);

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

        void LongClick(DataBean dataBean);
    }

}
