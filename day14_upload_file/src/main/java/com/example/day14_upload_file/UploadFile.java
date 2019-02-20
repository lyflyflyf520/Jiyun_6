package com.example.day14_upload_file;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class UploadFile {
    // 分割符,自己定义即可
    private static final String BOUNDARY = "----WebKitFo";
    private static final String TAG = "UploadFile";

    /**
     * 上传文件 到服务器
     *
     * @param params       post参数
     * @param fileFormName 文件在表单中的键 key=img  file =文件流
     * @param uploadFile   上传的文件
     * @param urlStr       上传服务器地址url
     * @throws Exception
     */
    public static void uploadFile(Map<String, String> params, String fileFormName, File uploadFile, String urlStr) throws Exception {

        String newFileName = uploadFile.getAbsolutePath().substring(uploadFile.getAbsolutePath().lastIndexOf("/") + 1);
        if (newFileName == null || newFileName.trim().equals("")) {
            newFileName = uploadFile.getName();
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                stringBuilder.append("Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n");
                stringBuilder.append("\r\n");
                stringBuilder.append(params.get(key) + "\r\n");
            }
        }

        stringBuilder.append("--" + BOUNDARY + "\r\n");
        stringBuilder.append("Content-Disposition: form-data; name=\"" + fileFormName + "\"; filename=\"" + newFileName + "\""
                + "\r\n");
        stringBuilder.append("Content-Type: image/jpg" + "\r\n");// 如果服务器端有文件类型的校验，必须明确指定ContentType
        stringBuilder.append("\r\n");
        byte[] headerInfo = stringBuilder.toString().getBytes("UTF-8");
        byte[] endInfo = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(10*1000);
        conn.setRequestProperty("Connection", "close");
        // 设置传输内容的格式，以及长度
        conn.setRequestProperty("Content-Type", "image/jpg; boundary=" + BOUNDARY);
        conn.setRequestProperty("Content-Length", (headerInfo.length + uploadFile.length() + endInfo.length) + "");
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
