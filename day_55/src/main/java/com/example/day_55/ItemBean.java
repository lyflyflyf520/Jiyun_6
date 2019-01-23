package com.example.day_55;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class ItemBean {

    /**
     * id : 8289
     * title : 油焖大虾
     * pic : http://www.qubaobei.com/ios/cf/uploadfile/132/9/8289.jpg
     * collect_num : 1666
     * food_str : 大虾 葱 生姜 植物油 料酒
     * num : 1666
     */
    @Id
    private Long Id; // 1.2.3

    private String foodId;
    private String title;
    private String pic;
    private String collect_num;
    private String food_str;
    @Transient
    private int num;


}
