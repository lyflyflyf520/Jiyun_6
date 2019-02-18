package com.example.day12;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemCache {
    private LruCache<String, Bitmap> mImageCache;

    // 初始化 - 构造


    public MemCache() {
        initCache();
    }

    private void initCache() {
        // 计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 取4分之一的可用内存作为缓存
        final int cacheSize = maxMemory / 4;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    /**
     * 保存数据--bitmap
     * @param url  图片的name
     * @param bitmap
     */
    public void putBitmap(String url, Bitmap bitmap) {
        if (mImageCache != null) {

            mImageCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmap(String url){
       return mImageCache.get(url);
    }
}
