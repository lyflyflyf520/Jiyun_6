package com.example.day12;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * 统一管理内存缓存和硬盘缓存
 *
 */
public class ImageCacheUtil {

    //

    MemCache memCache;
    DiskCache diskCache;

    public ImageCacheUtil(Context context) {
        this.memCache= new MemCache();
        this.diskCache= new DiskCache(Utils.getDiskCacheDir(context, "cache"), 10 * 1024* 1024);

    }

    /**
     * 保存数据到  双缓存
     * @param url
     * @param bitmap
     */
    public void saveBitmap(String url,Bitmap bitmap){

        if (memCache!=null){
            memCache.putBitmap(url,bitmap);
        }
        if (diskCache!=null){
            diskCache.saveBitmap(url,bitmap);
        }
    }

    /**
     * 获取缓存，先从内存里获取，如果没有，从磁盘里获取数据bitmap
     * @param url
     * @return
     */
    public Bitmap getBitmap(String url){

        Bitmap bitmap = memCache.getBitmap(url);

        if (bitmap==null){

            bitmap = diskCache.getBitmap(url);
        }
        return bitmap;
    }

}
