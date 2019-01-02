package com.jiyun.com.jiyun_z5.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Urls {

    public static final String  PAKEAGE_NAME="com.jiyun.com.jiyun_z5.";
    public static final String  url1="https://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";
    public static final List<HashMap<String,String>> classList = new ArrayList<>();

    static {
        HashMap<String,String>  className = new HashMap<>();
        className.put("XRecyclerViewActivity","test xRecyclerview");

        classList.add(className);

    }
}
