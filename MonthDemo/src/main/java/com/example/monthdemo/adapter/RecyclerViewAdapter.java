package com.example.monthdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.monthdemo.R;
import com.example.monthdemo.bean.WarBean;
import com.example.monthdemo.uitls.DbUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context context;
    private List<WarBean>  itemBeans = new ArrayList<>();
    private List<Boolean>  itemBeanStatus = new ArrayList<>();

    private boolean isEdit;//是否是编辑模式
    private boolean isAll;//是否全选

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    private static final String TAG = "RecyclerViewAdapter";
    /**
     * 删除  刷新数据
     * @param
     */
    public void delete(){
        for (int i = itemBeans.size()-1; i >=0; i--){// 从后往前删除

            if (itemBeanStatus.get(i)){ // 当前的位置如果是true ,证明被选中
                Log.d(TAG, "delete: "+i);
                WarBean warBean = itemBeans.get(i);
                DbUtils.delete(warBean);
                itemBeans.remove(warBean);

                itemBeanStatus.remove(i);

            }
        }
        notifyDataSetChanged();
    }

    /**
     * 设置 编辑模式，刷新数据
     * @param isEdit
     */
    public void setEditMode(boolean isEdit){
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }
    /**
     * 设置全选模式
     * @param isAll
     */
    public void selectAllMode(boolean isAll){
        this.isAll = isAll;

        for (int x=0;x<itemBeanStatus.size();x++){
            itemBeanStatus.set(x,isAll);
        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root  = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,null);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final WarBean itemBean = itemBeans.get(position);
        holder.textView.setText(itemBean.getTitle());
        if (isEdit){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else{
            holder.checkBox.setVisibility(View.GONE);
        }
        final boolean status = itemBeanStatus.get(position);
        if (status){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (status){  // 获取当前的选中状态，反之
                  holder.checkBox.setChecked(false);
                  itemBeanStatus.set(position,false);
              }else{
                  holder.checkBox.setChecked(true);
                  itemBeanStatus.set(position,true);
              }
              notifyDataSetChanged();
            }
        });

        Glide.with(context).load(itemBean.getImgsrc()).into(holder.img);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (onLongClick!=null){
                    onLongClick.onLongItemClick(itemBean);
                }
                return false;
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemBeans.size();
    }

    public void updateDate(List<WarBean> itemBeans) {

        this.itemBeans = itemBeans;
        itemBeanStatus.clear();
        for (WarBean warBean:itemBeans){
            itemBeanStatus.add(false);
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public CheckBox checkBox;
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);


            checkBox= itemView.findViewById(R.id.checkbox);
            img= itemView.findViewById(R.id.img);
            textView= itemView.findViewById(R.id.title);
        }
    }


    public interface onLongClick{
        void onLongItemClick(WarBean warBean);
    }
    private onLongClick onLongClick;
    public void setOnLongClick(onLongClick onLongClick){
        this.onLongClick = onLongClick;
    }
}
