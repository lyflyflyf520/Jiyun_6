package com.example.morethreadloadfile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.morethreadloadfile.DownLoadUtils.restTask;

public class ThreadTask implements Runnable {

    private int partSize;
    //当前线程的下载位置
    private int startPos;
    //当前下载保存到的目标文件（块）
    private RandomAccessFile currentPart;
    //当前已下载数据大小
    private int currentDownLoaded;

    public ThreadTask(int startPos, int partSize,String targetFilePathAndName) throws IOException {
        this.startPos = startPos;
        this.partSize = partSize;

        // 创建本地的文件，定位到起点位置，便于当前线程从起点位置开始下载
        currentPart = new RandomAccessFile(targetFilePathAndName, "rw");
        currentPart.seek(startPos);
    }


    @Override
    public void run() {

        doHttpTask();
    }

    private static final String TAG = "ThreadTask";

    /**
     * 去网络读取服务器文件的流inputStream
     * 然后写入到本地文件里
     */
    private void doHttpTask() {
        try {
            HttpURLConnection connection = DownLoadUtils.getConnection();
            InputStream in = connection.getInputStream();
            skipFully(in, startPos);
            byte[] bytes = new byte[8*1024];
            int hasRead;
            // 开始读取流的操作
            while ((currentDownLoaded < partSize) && (hasRead = in.read(bytes)) > 0) {
                currentPart.write(bytes, 0, hasRead);// 写入数据到本地文件
                currentDownLoaded += hasRead;

                Log.d(TAG, "doHttpTask: thread="+Thread.currentThread().getName()+"--"+currentDownLoaded);
            }
            // 关闭各种流操作
            currentPart.close();
            in.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            restTask--;
            if (restTask == 0){// 当前下载的线程数量为0，下载完成
                new DownLoadUtils().installAPK();
                Log.d(TAG, "doHttpTask: 下载完成");
            }

        }

    }

    /**
     * 从输入流中从起点开始跳过指定长度
     *
     * @param in    输入流
     * @param bytes 要跳过的字节数
     * @throws IOException
     */
    public final void skipFully(InputStream in, long bytes) throws IOException {
        long len;
        while (bytes > 0) {
            len = in.skip(bytes);
            bytes -= len;
        }
    }
}
