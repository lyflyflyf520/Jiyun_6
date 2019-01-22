package com.example.day_55;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * 数据源  --业务数据的集合
 *
 * 状态集合--负责管理item的状态
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {


    List<ItemBean>  dataBeans = new ArrayList<>();
    Context context;

    public MyRecyclerAdapter(List<ItemBean> dataBeans, Context context) {
        this.dataBeans = dataBeans;
        this.context = context;

    }


    @NonNull
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(context).inflate(R.layout.recycler_item,null);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
         ItemBean dataBean = dataBeans.get(position);
        holder.textView.setText(dataBean.getTitle());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onLongClick!=null){

                    onLongClick.itemOnLong(position);
                }

                return false;
            }
        });

    }



    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public void updateData(List<ItemBean> dataBeans) {
        this.dataBeans = dataBeans;

        notifyDataSetChanged();

    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        CheckBox checkBox;
        ImageView img;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.title);


        }
    }

    OnLongItemClick onLongClick;
    interface  OnLongItemClick{
        void itemOnLong(int postion);
    }


    public void setOnLongClick(OnLongItemClick onLongClick){
        this.onLongClick = onLongClick;
    }
}
