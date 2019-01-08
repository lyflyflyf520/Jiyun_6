package com.jiyun.com.jiyun_z5.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BannerItem {

    private String desc;
    private int id;
    private String imagePath;
    private String title;
    private String url;


    public String toString(){

        return "id="+id+"--title="+title+"--imgpath="+imagePath+"--desc="+desc;
    }
    @Generated(hash = 106082948)
    public BannerItem(String desc, int id, String imagePath, String title,
            String url) {
        this.desc = desc;
        this.id = id;
        this.imagePath = imagePath;
        this.title = title;
        this.url = url;
    }

    @Generated(hash = 104306708)
    public BannerItem() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
