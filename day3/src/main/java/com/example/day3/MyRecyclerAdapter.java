package com.example.day3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.day3.bean.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源  --业务数据的集合
 *
 * 状态集合--负责管理item的状态
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {


    List<Food.DataBean>  dataBeans = new ArrayList<>();
    List<Boolean>  status = new ArrayList<>();
    Context context;

    public MyRecyclerAdapter(List<Food.DataBean> dataBeans, Context context) {
        this.dataBeans = dataBeans;
        this.context = context;

        initStatus(dataBeans);
    }

    /**
     * 初始化 状态集合 默认false
     * @param dataBeans
     */
    private void initStatus(List<Food.DataBean> dataBeans){
        for (Food.DataBean dataBean:dataBeans){
            status.add(false);
        }
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(context).inflate(R.layout.recycler_item,null);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
         Food.DataBean dataBean = dataBeans.get(position);
        holder.textView.setText(dataBean.getTitle());

        /**  根据集合里的状态 ，反应到UI-checkbox**/
        final boolean sta = status.get(position);
        holder.checkBox.setChecked(sta);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                false  ---->   true
                //判断当前对象 是点击状态吗==true？
                if (sta){
//                    holder.checkBox.setChecked(false);
                    status.set(position,false);// 改变集合里的状态
                }else {
//                    holder.checkBox.setChecked(true);
                    status.set(position,true);// 改变集合里的状态
                }
                notifyDataSetChanged();

            }
        });

        Glide.with(context).load(dataBean.getPic()).into(holder.img);

    }



    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public void updateData(List<Food.DataBean> dataBeans) {
        this.dataBeans = dataBeans;

        initStatus(dataBeans);
        notifyDataSetChanged();

    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        CheckBox checkBox;
        ImageView img;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkbox);
            img = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.title);

        }
    }
}
