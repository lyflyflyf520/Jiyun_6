package com.example.morethreaduploadfile.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpUtils {


    /**
     * 下载文件
     *
     * @param urlAddress  文件url地址
     * @param downloadDir 文件保存的目录
     * @return 文件
     */
    public static File downloadFile(String urlAddress, String downloadDir) {

        try {
            // 创建URL对象
            URL url = new URL(urlAddress);
            // 获取连接对象
            URLConnection urlConnection = url.openConnection();
            // 设置允许输入流输入数据到本地
            urlConnection.setDoInput(true);
            // 设置允许输出流输出到服务器
            urlConnection.setDoOutput(true);
            // 获取内容长度
            int fileLength = urlConnection.getContentLength();
            // 获取文件url径名称
            String filePathName = urlConnection.getURL().getFile();
            // 获取文件名称
            String fileName = filePathName.substring(filePathName.lastIndexOf(File.separatorChar) + 1);

            // 定义文件下载的目录与名称
            String path = downloadDir + File.separatorChar + fileName;

            // 实例化文件对象
            File file = new File(path);

            // 判断文件路径是否存在
            if (!file.getParentFile().exists()) {
                // 如果文件不存在就创建文件
                file.getParentFile().mkdirs();
            }

            // 从连接对象中获取输入字节流
            InputStream inputStream = urlConnection.getInputStream();

            // 实例化输入流缓冲区，将输入字节流传入
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            // 实例化输出流对象，将文件对象传入
            OutputStream outputStream = new FileOutputStream(file);

            // 定义整形变量用来接收读取到的文件大小
            int size;
            // 定义整形变量用来累计当前读取到的文件长度
            int len = 0;
            // 定义字节数组对象，用来从输入缓冲区中装载数据块
            byte[] buf = new byte[1024];
            // 从输入缓冲区中一次读取1024个字节的文件内容到buf对象中，并将读取大小赋值给size变量，当读取完毕后size=-1，结束循环读取
            while ((size = bufferedInputStream.read(buf)) != -1) {
                // 累加每次读取到的文件大小
                len += size;
                // 向输出流中写出数据
                outputStream.write(buf, 0, size);
                // 打印当前文件下载的百分比
                System.out.println("下载进度：" + len * 100 / fileLength + "%\n");
            }
            // 关闭输出流
            outputStream.close();
            // 关闭输入缓冲区
            bufferedInputStream.close();
            // 关闭输入流
            inputStream.close();

            // 返回文件对象
            return file;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String TAG = "HttpUtils";

    /**
     * 2
     * HttpUrlConnection　实现文件上传
     * 3
     * <p>
     * 4
     *
     * @param params       普通参数
     *                     5
     * @param fileFormName 文件在表单中的键
     *                     6
     * @param uploadFile   上传的文件
     *                     7
     * @param newFileName  文件在表单中的值（服务端获取到的文件名）
     *                     8
     * @param urlStr       url
     *                     9
     * @throws IOException 10
     */

    public static void uploadForm(Map<String, String> params, String fileFormName, File uploadFile, String newFileName, String urlStr) throws IOException {


        if (newFileName == null || newFileName.trim().equals("")) {

            newFileName = uploadFile.getName();

        }
        StringBuilder sb = new StringBuilder();
        /**
         * 普通的表单数据
         */
        if (params != null) {
            for (String key : params.keySet()) {
                sb.append("Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n");
                sb.append("\r\n");
                sb.append(params.get(key) + "\r\n");
            }
        }
        /**
         30
         * 上传文件的头
         31
         */
        sb.append("--" + BOUNDARY + "\r\n");
        sb.append("Content-Disposition: form-data; name=\"" + fileFormName + "\"; filename=\"" + newFileName + "\""
                + "\r\n");
        sb.append("Content-Type: application/octet-stream" + "\r\n");// 如果服务器端有文件类型的校验，必须明确指定ContentType
        sb.append("\r\n");
        byte[] headerInfo = sb.toString().getBytes("UTF-8");
        byte[] endInfo = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        // 设置传输内容的格式，以及长度
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        conn.setRequestProperty("Content-Length",
                String.valueOf(headerInfo.length + uploadFile.length() + endInfo.length));
        conn.setDoOutput(true);
        OutputStream out = conn.getOutputStream();
        InputStream in = new FileInputStream(uploadFile);
        //写入的文件长度
        int count = 0;
        //文件的总长度
        int available = in.available();
        // 写入头部 （包含了普通的参数，以及文件的标示等）
        out.write(headerInfo);
        // 写入文件
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
            count += len;
            int progress = count * 100 / available;
            Log.d(TAG, "上传进度: " + progress + " %");
//            updateProgress(progress);
        }
        // 写入尾部
        out.write(endInfo);
        in.close();
        out.close();
        if (conn.getResponseCode() == 200) {
            System.out.println("文件上传成功");
            String result = stream2String(conn.getInputStream());
            Log.d(TAG, "uploadForm: " + result);
        }
    }

    // 分割符,自己定义即可
    private static final String BOUNDARY = "----WebKitFormBoundaryT1HoybnYeFOGFlBR";

    public static String stream2String(InputStream is) {
        int len;
        byte[] bytes = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while ((len = is.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



}
