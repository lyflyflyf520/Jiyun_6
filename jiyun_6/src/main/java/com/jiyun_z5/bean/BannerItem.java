package com.jiyun_z5.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class BannerItem {

    private String desc;
    @Id(autoincrement = true)
    private long id;
    private String imagePath;
    private String title;
    private String url;
    @Generated(hash = 2052441497)
    public BannerItem(String desc, long id, String imagePath, String title,
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
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    

}
