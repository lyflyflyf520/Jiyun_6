package com.jiyun_z5.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiyun_z5.utils.Urls;
import com.jiyun_z5.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class XutilAdapter extends XRecyclerView.Adapter<XutilAdapter.ViewHolder> {


    private Context context;

    public XutilAdapter(Context context) {
        this.context = context;

    }
    public String getKey(int i) {
        HashMap<String, String> map = Urls.classList.get(i);

        return map.keySet().iterator().next();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_item, null, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        HashMap<String, String> itemMap = Urls.classList.get(position);

//        viewHolder.img
//        x.image().bind(viewHolder.img,text);
        String key = itemMap.keySet().iterator().next();
        viewHolder.title.setText(key);
        viewHolder.text.setText(itemMap.get(key));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemOnClickListener != null) {
                    itemOnClickListener.setOnItemClickListener(position);
                }
            }
        });
    }



    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return Urls.classList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView text;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.item_title);
            text = view.findViewById(R.id.item_text);
        }
    }

    ItemOnClickListener itemOnClickListener;

    public void setOnItemClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public interface ItemOnClickListener {

        void setOnItemClickListener(int position);
    }

}
