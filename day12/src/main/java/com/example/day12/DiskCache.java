package com.example.day12;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DiskCache {

    private static final String TAG = "DiskCache";
    DiskLruCache mDiskLruCache;
    // 初始化


    public DiskCache(File directory, long maxSize) {
        try {
            mDiskLruCache = DiskLruCache.open(directory, 1, 1, maxSize);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存bitmap到磁盘
     * 通过图片url 然后md5 加密的内容，作为key  来保存bitmap 对象
     *
     * @param url    图片请求url  --》 md5  去除特殊字符
     * @param bitmap
     */
    public void saveBitmap(String url, Bitmap bitmap) {

        try {
            String key = Utils.hashKeyForDisk(url);
            //editor 操作数据保存逻辑
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            OutputStream os = editor.newOutputStream(0);
            //此处存的一个 bitmap 对象因此用 ObjectOutputStream
            ObjectOutputStream outputStream = new ObjectOutputStream(os);

            // 下载图片
            if (downloadImage(url, outputStream)) {
                editor.commit();

            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
            //别忘了关闭流和提交编辑
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过url  获取md5加密后的key。然后通过key 获取bitmap 对象
     *
     * @param url
     * @return
     */
    public Bitmap getBitmap(String url) {
        //使用DiskLruCache获取缓存，需要传入key，而key是imageUrl加密后的字符串，
        Bitmap bitmap = null;
        String key = Utils.hashKeyForDisk(url);
        //通过key获取的只是一个快照
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (snapshot != null) {
            InputStream inputStream = snapshot.getInputStream(0);//类似写缓存时候，传入的是缓存的编号
            //可以使用bitmapFactory
            bitmap = BitmapFactory.decodeStream(inputStream);
        }

        return bitmap;
    }


    /**
     * 下载图片
     *
     * @param imgUrl       图片网址链接
     * @param outputStream 输出流对象
     * @return 返回时候完成下载成功
     */
    private boolean downloadImage(String imgUrl, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            URL url = new URL(imgUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);//Buffer输入流，8M大小的缓存
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;//正在读取的byte
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //关闭资源
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
