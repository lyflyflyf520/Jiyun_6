package com.example.morethreaduploadfile;

import com.example.morethreaduploadfile.utils.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DownThread implements Runnable {

    String url;
    String filePath;

    public DownThread(String url, String filePath) {
        this.url = url;
        this.filePath = filePath;
    }

    @Override
    public void run() {

//        String result = HttpUtils.uploadFile(url, filePath);

        HashMap<String,String> map  = new HashMap<>();
        map.put("key","xxx");

        try {
            HttpUtils.uploadForm(map, "file",new File(filePath),"55.jpg",url);
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        if (result!=null&&iUploadListener!=null){
//            iUploadListener.uploadSuccess(result);
//        }

    }

    IUploadListener iUploadListener;

    public void setUploadListener(IUploadListener iUploadListener) {
        this.iUploadListener = iUploadListener;
    }

    public interface IUploadListener {

        void uploadSuccess(String result);
    }
}
