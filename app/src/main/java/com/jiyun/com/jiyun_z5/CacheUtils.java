package com.jiyun.com.jiyun_z5;

import java.util.HashMap;

public class CacheUtils {

    static HashMap<String,String > hashMap = new HashMap<>();

    public static void saveJSONData(String key,String value){

        if (hashMap!=null){
            hashMap.put(key,value);
        }
    }

    public static String getJSONData(String key ){
        String result="";
        if (hashMap!=null){
            result = hashMap.get(key);
        }
        return result;
    }
}
