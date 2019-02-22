package com.example.morethreadloadfile;

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
 * 管理多线程的下载
 *  1.获取下载的文件大小--去服务器读取  10MB
 *  2.获取文件大小后，根据线程的数量来分割 每个线程下载应该下载的文件大小(startpos--endpos)
 *  3.最终每个线程去下载各自的文件片段
 */
public class DownLoadUtils {



    //默认下载文件保存路径
    private final String DEFAULT_TARGET_FOLDER_PATH = Environment.getExternalStorageDirectory() + File.separator;
    //下载文件保存的目标路径（包括文件名）
    private static String targetFilePathAndName;
    //最大可开启的线程数
    public static final int MAX_THREAD_NUMBER = 15;
    //下载文件的URL地址
    public static String sourcePath;

    //下载所需开启的线程数，默认为5个
    private int threadNumber = 5;

    //还在下载的线程数量
    public static int restTask;
    //下载文件总大小
    private int fileSize;
    //每个线程负责下载的文件块大小
    private int partSize;
    static Context context;
    /**
     * 开始一次下载
     * @param sourcePath 目标URL
     * @param targetFilePath 目标保存路径
     * @param threadNumber 开启的线程数
     * @param fileName 保存的文件名
     * @return 开启任务成功否
     */
    public boolean start( Context context, String sourcePath,  String targetFilePath, int threadNumber,   String fileName) throws IOException {
        this.sourcePath = sourcePath;
        this.context = context;
        this.targetFilePathAndName = targetFilePath == null ? DEFAULT_TARGET_FOLDER_PATH
                + (fileName == null ? System.currentTimeMillis() : fileName) :
                targetFilePath + (fileName == null ? System.currentTimeMillis() : fileName);
        this.threadNumber = threadNumber < 0 || threadNumber > MAX_THREAD_NUMBER ? this.threadNumber : threadNumber;
        this.restTask = this.threadNumber;

        // 1.获取下载的文件大小--去服务器读取  10MB
        // 2.获取文件大小后，根据线程的数量来分割 每个线程下载应该下载的文件大小(startpos--endpos)
        // 3.最终每个线程去下载各自的文件片段
//        4.启动多个线程 去真实下载文件
        HttpURLConnection conn = getConnection();
        fileSize = conn.getContentLength();
        conn.disconnect();

        // 创建本地 下载的文件
        RandomAccessFile file = new RandomAccessFile(targetFilePathAndName, "rw");
        file.setLength(fileSize);// 设置大小
        file.close();

        // 文件分割
        partSize = fileSize / threadNumber + 1;
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNumber; i++) {
            int startPos = i * partSize;

            // 开始下载 每个任务，从起点到重点
            executorService.execute(new ThreadTask(startPos,partSize,targetFilePathAndName));
        }
        executorService.shutdown();
        return true;
    }

    public static HttpURLConnection getConnection() throws IOException {
        URL url = new URL(DownLoadUtils.sourcePath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10*1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
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
    public static void installAPK( ) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            File file = new File(targetFilePathAndName);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.example.morethreadloadfile", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(targetFilePathAndName)),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

}
