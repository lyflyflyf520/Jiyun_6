package com.example.mvp_34;

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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final String TAG = "MyAdapter";
    private Context context;
    private List<DataBean> dataBeans = new ArrayList<>();

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<DataBean> dataBeans) {
        if (this.dataBeans != null) {
            this.dataBeans.clear();
            this.dataBeans.addAll(dataBeans);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.recy_item, null);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

//        viewHolder.mImg.setImageBitmap();
        DataBean dataBean = dataBeans.get(i);
        String picPath = dataBean.getPic();
        if (picPath.contains("http")) {
            picPath = picPath.replace("http", "https");
        }
        Glide.with(context).load(picPath)
                .into(viewHolder.mImg);
        viewHolder.mContent.setText(dataBean.getTitle());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onLongClickListener!=null){
                    onLongClickListener.onLongItem(i);
                }
                return false;
            }

        });
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public DataBean getDataBean(int pos){

        return dataBeans.get(pos);
    }

    public void deleteDataBean(DataBean dataBean){

        if (dataBeans.contains(dataBean)){
            dataBeans.remove(dataBean);
            notifyDataSetChanged();
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView mImg;
        TextView mContent;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.mImg = (ImageView) view.findViewById(R.id.img);
            this.mContent = (TextView) view.findViewById(R.id.content);
        }
    }

    public OnLongClickListener onLongClickListener;

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {

        this.onLongClickListener = onLongClickListener;
    }

    public interface OnLongClickListener {
        void onLongItem(int position);
    }
}
