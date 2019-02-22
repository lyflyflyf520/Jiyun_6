package com.example.day14;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1.获取服务器文件的大小
 *
 *
 * 启动数组里的线程
 */
public class DownLoadUtils {


    private static String sourcePath;
    //下载文件总大小
    private int fileSize;
    //最大可开启的线程数
    private int MAX_THREAD_NUMBER=15;

    //下载所需开启的线程数，默认为5个
    private int threadNumber=5;

    private ItemTask[] threads;
    //还在下载的线程数量
    public static int restTask;

    //默认下载文件保存路径
    private final String DEFAULT_TARGET_FOLDER_PATH = Environment.getExternalStorageDirectory() + File.separator;
    //下载文件保存的目标路径（包括文件名）
    private static String targetFilePathAndName;

    //每个线程负责下载的文件块大小
    private int partSize;
    /**
     * 开始下载文件
     * @param sourcePath 目标URL
     * @param targetFilePath 目标保存路径
     * @param threadNumber 开启的线程数
     * @param fileName 保存的文件名
     * @return 开启任务成功否
     */
    public  void startDownFile
    (Context context, String sourcePath,  String targetFilePath, int threadNumber, String fileName) throws IOException {

        this.sourcePath = sourcePath;
        this.threadNumber = threadNumber < 0 || threadNumber > MAX_THREAD_NUMBER ? this.threadNumber : threadNumber;
        this.restTask = this.threadNumber;
        this.targetFilePathAndName = targetFilePath == null ? DEFAULT_TARGET_FOLDER_PATH
                + (fileName == null ? System.currentTimeMillis() : fileName) :
                targetFilePath + (fileName == null ? System.currentTimeMillis() : fileName);

        threads = new ItemTask[this.threadNumber];
        HttpURLConnection conn = getConnection();
        fileSize = conn.getContentLength();// 文件的大小
        conn.disconnect();

        // new File (创建一个文件，用于保存数据)
        RandomAccessFile file = new RandomAccessFile(targetFilePathAndName, "rw");
        file.setLength(fileSize);
        file.close();

        partSize = fileSize / threadNumber + 1;


        ExecutorService  executorService = Executors.newCachedThreadPool();

        for (int x=0;x<threadNumber;x++){

            int startPos = x * partSize;
            ItemTask itemTask = new ItemTask(startPos ,partSize,targetFilePathAndName);
            executorService.execute(itemTask);
        }
        executorService.shutdown();// 关闭 服务--除了上述的任务，不再接收任务

    }

    /**
     * 获取connection,同时添加配置
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getConnection() throws IOException {
        URL url = new URL(DownLoadUtils.sourcePath);// 服务器的文件地址url
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5*1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "image/gif, image/jpeg, " +
                "image/pjpeg, image/pjpeg, application/x-shockwave-flash, " +
                "application/xaml+xml, application/vnd.ms-xpsdocument," +
                " application/x-ms-xbap, application/x-ms-application, " +
                "application/vnd.ms-excel, application/vnd.ms-powerpoint," +
                " application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Referer", DownLoadUtils.sourcePath);
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Accept-Encoding", "identity");
//        conn.connect();
        return conn;
    }


    //普通安装
    private static void installNormal(Context context,String apkPath) {

    }

}
