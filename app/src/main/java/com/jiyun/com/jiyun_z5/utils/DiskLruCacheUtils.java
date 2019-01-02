package utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 硬盘缓存
 */
public class DiskLruCacheUtils {

    private static final String TAG = "DiskLruCacheUtils";
    private static final int MAX_SIZE = 10 * 1024 * 1024;//10MB
    private static final int IO_BUFFER_SIZE = 1024;
    private DiskLruCache diskLruCache;

    public DiskLruCacheUtils(Context context) {

        if (diskLruCache == null || diskLruCache.isClosed()) {
            try {
                File cacheDir = new File(context.getCacheDir().getPath(), "CacheDir");

                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                }
                //初始化DiskLruCache
                diskLruCache = DiskLruCache.open(cacheDir, 1, 1, MAX_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param key imgFilepath
     */
    public void saveBitmap2Disk(final String key) {


        new Thread() {
            public void run() {
                try {
                    DiskLruCache.Editor editor = diskLruCache.edit(hashKeyForDisk(key));
                    if (editor != null) {

                        OutputStream outputStream = editor.newOutputStream(0);
                        downloadUrlToStream(key, outputStream);
                        editor.commit();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    public Bitmap getBitmap2key(String key) {
        Bitmap bitmap = null;

        try {

            DiskLruCache.Snapshot snapshot = diskLruCache.get(hashKeyForDisk(key));
            if (snapshot != null) {
                InputStream is = snapshot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(is);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 实现根据传入的urlString从网络中下载图片数据，然后写入输出流os中
     */
    public boolean downloadUrlToStream(String urlString, OutputStream os) {
        //定义连接和输入输出流
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            //获取到连接和输入输出流
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(os, IO_BUFFER_SIZE);
            //进行将网络数据写入os中
            int b = -1;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            Log.d(TAG, "downloadBitmap failed." + e);
        } finally {
            //最后关闭连接和流
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (in != null) {

                    in.close();
                }
                if (out != null) {

                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
