package com.example.day13_photo_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public  class MyAdapter extends BaseAdapter{
    private Context context;
    ArrayList<FileInfo> fileInfos = new ArrayList<>();
    public MyAdapter(Context context) {
        this.context = context;
    }

    /**
     * 更新数据，并刷新适配器
     * @param fileInfos
     */
    public  void updateListData(ArrayList<FileInfo> fileInfos){
        this.fileInfos = fileInfos;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return fileInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return fileInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.listview_item,null);


        ImageView imageView = convertView.findViewById(R.id.img);
        TextView nameTv = convertView.findViewById(R.id.name);
        FileInfo  fileInfo = fileInfos.get(position);

        imageView.setImageBitmap(fileInfo.getThumbnail());
        nameTv.setText(fileInfo.getFileName());

        return convertView;
    }
}
