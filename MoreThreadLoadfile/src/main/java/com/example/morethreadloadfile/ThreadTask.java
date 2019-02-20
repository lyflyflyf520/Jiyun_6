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

        currentPart = new RandomAccessFile(targetFilePathAndName, "rw");
        currentPart.seek(startPos);
    }


    @Override
    public void run() {

        doHttpTask();
    }

    private static final String TAG = "ThreadTask";
    private void doHttpTask() {
        try {
            HttpURLConnection connection = DownLoadUtils.getConnection();
            InputStream in = connection.getInputStream();
            skipFully(in, startPos);
            byte[] bytes = new byte[8*1024];
            int hasRead;
            while ((currentDownLoaded < partSize) && (hasRead = in.read(bytes)) > 0) {
                currentPart.write(bytes, 0, hasRead);
                currentDownLoaded += hasRead;

                Log.d(TAG, "doHttpTask: thread="+Thread.currentThread().getName()+"--"+currentDownLoaded);
            }
            currentPart.close();
            in.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            restTask--;
            if (restTask == 0){
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
