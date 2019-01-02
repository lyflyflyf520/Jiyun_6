package com.jiyun.com.jiyun_z5;

import android.content.Context;
import android.graphics.Bitmap;

import utils.CacheUtils2;
import utils.DiskLruCacheUtils;

/**
 * 负责管理内存缓存和磁盘缓存
 */
public class ImageLoader {

    private Context context;

    public ImageLoader(Context context) {
        this.context = context;
        cacheUtils = new CacheUtils2();
        diskLruCacheUtils = new DiskLruCacheUtils(context);
    }

    private CacheUtils2 cacheUtils;
    private DiskLruCacheUtils diskLruCacheUtils;


    public void saveMemoryCache(String key, Bitmap bitmap) {
        if (cacheUtils.getBitmapFromMemCache(key) == null) {
            cacheUtils.addBitmapToMemory(key, bitmap);
        }
        if (diskLruCacheUtils.getBitmap2key(key) == null) {
            diskLruCacheUtils.saveBitmap2Disk(key);
        }

    }

    public Bitmap getBitmapFromCache(String key) {
        Bitmap bitmap = null;
        if (cacheUtils.getBitmapFromMemCache(key) != null) {
            bitmap = cacheUtils.getBitmapFromMemCache(key);
        } else {
            if (diskLruCacheUtils.getBitmap2key(key) != null) {
                return diskLruCacheUtils.getBitmap2key(key);
            }
        }
        return bitmap;
    }
}
