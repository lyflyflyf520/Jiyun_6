package com.example.monthdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.monthdemo.R;
import com.example.monthdemo.bean.WarBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context context;
    private List<WarBean>  itemBeans = new ArrayList<>();


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
        WarBean itemBean = itemBeans.get(position);
        holder.textView.setText(itemBean.getTitle());

        Glide.with(context).load(itemBean.getImgsrc()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return itemBeans.size();
    }

    public void updateDate(List<WarBean> itemBeans) {

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

}
