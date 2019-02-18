package com.example.day12;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    private static final String TAG = "Utils";
    /**
     * 获取缓存文件夹，这里优先选择SD卡下面的android/data/packageName/cache/路径，若没有SD卡，就选择data/data/packageName/cache
     *
     * @param context    上下文环境
     * @param uniqueName 缓存文件夹名称
     * @return 返回缓存文件
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        File file = new File(cachePath + File.separator + uniqueName);
        Log.d(TAG, "getDiskCacheDir: file="+file.getAbsolutePath());
        return file;
    }
    /**
     * 获取本App的版本号
     *
     * @param context context上下文
     * @return 返回版本号
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    /**
     * 给字符串来个md5加密，
     * @param key 需要加密的string
     * @return 返回加密后的string ，或者加密失败，就返回string的哈希值
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            //md5加密
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //若md5加密失败，就用哈希值
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }
    /**
     * 字节数组转为十六进制字符串
     * @param bytes 字节数组
     * @return 返回十六进制字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length()==1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
