package com.example.morethreadloadfile;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownLoadUtils {



    //默认下载文件保存路径
    private final String DEFAULT_TARGET_FOLDER_PATH = Environment.getExternalStorageDirectory() + File.separator;
    //下载文件保存的目标路径（包括文件名）
    private String targetFilePathAndName;
    //最大可开启的线程数
    public static final int MAX_THREAD_NUMBER = 15;
    //下载文件的URL地址
    public static String sourcePath;

    //下载所需开启的线程数，默认为5个
    private int threadNumber = 5;

    //还在下载的线程数量
    public static int restTask;
    private ThreadTask[] threads;
    //下载文件总大小
    private int fileSize;
    //每个线程负责下载的文件块大小
    private int partSize;
    /**
     * 开始一次下载
     * @param sourcePath 目标URL
     * @param targetFilePath 目标保存路径
     * @param threadNumber 开启的线程数
     * @param fileName 保存的文件名
     * @return 开启任务成功否
     */
    public boolean start(  String sourcePath,  String targetFilePath, int threadNumber,   String fileName) throws IOException {
        this.sourcePath = sourcePath;
        this.targetFilePathAndName = targetFilePath == null ? DEFAULT_TARGET_FOLDER_PATH
                + (fileName == null ? System.currentTimeMillis() : fileName) :
                targetFilePath + (fileName == null ? System.currentTimeMillis() : fileName);
        this.threadNumber = threadNumber < 0 || threadNumber > MAX_THREAD_NUMBER ? this.threadNumber : threadNumber;
        this.restTask = this.threadNumber;
        threads = new ThreadTask[this.threadNumber];

        HttpURLConnection conn = getConnection();
        fileSize = conn.getContentLength();
        conn.disconnect();

//        if (fileSize <= 0) {
//            downloadFinish.onComplete(null);
//            return false;
//        }

        RandomAccessFile file = new RandomAccessFile(targetFilePathAndName, "rw");
        file.setLength(fileSize);
        file.close();

        partSize = fileSize / threadNumber + 1;
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNumber; i++) {
            int startPos = i * partSize;

            executorService.execute(new ThreadTask(startPos,partSize,targetFilePathAndName));
        }
        executorService.shutdown();
        return true;
    }

    public static HttpURLConnection getConnection() throws IOException {
        URL url = new URL(DownLoadUtils.sourcePath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5*1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Referer", DownLoadUtils.sourcePath);
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        conn.setRequestProperty("Connection", "Keep-Alive");
//        conn.connect();
        return conn;
    }
}
