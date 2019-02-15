package com.example.demo214;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.load.DataSource;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

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


        setImageUri(myViewHolder,dataBean.getImgsrc());

//        GlideApp.with(context).load(dataBean.getImgsrc())
//                .centerCrop()
//                .error(R.drawable.ic_launcher_background)
//                .placeholder(R.drawable.ic_launcher_background)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(myViewHolder.mImg);

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


    /**
     * 使用fresco 加载图片
     * @param viewHolder
     * @param imgUrl
     */
    private void setImageUri(MyViewHolder viewHolder,String imgUrl){

//        Uri uri = Uri.parse(imgUrl);
//        viewHolder.mImg.setImageURI(uri);

        ControllerListener controllerListener = new BaseControllerListener() {
            @Override
            public void onSubmit(String id, Object callerContext) {

                Log.d(TAG, "onSubmit: ");
            }

            @Override
            public void onFinalImageSet(String id, @Nullable Object imageInfo, @Nullable Animatable animatable) {

                Log.d(TAG, "onFinalImageSet: ");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {

                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onRelease(String id) {

                Log.d(TAG, "onRelease: ");
            }
        };

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(Uri.parse(imgUrl))
                .build();

        viewHolder.mImg.setController(controller);




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
        SimpleDraweeView mImg;
        TextView mTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mImg = (SimpleDraweeView) itemView.findViewById(R.id.img);
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
