package com.jiyun_z5.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class BannerItem {

    private String desc;
    @Property(nameInDb = "bid")
    @Id(autoincrement = true)
    private Long bid;
    private long itmeId;
    private String imagePath;
    private String title;
    private String url;
    @Generated(hash = 2132343279)
    public BannerItem(String desc, Long bid, long itmeId, String imagePath,
            String title, String url) {
        this.desc = desc;
        this.bid = bid;
        this.itmeId = itmeId;
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
    public Long getBid() {
        return this.bid;
    }
    public void setBid(Long bid) {
        this.bid = bid;
    }
    public long getItmeId() {
        return this.itmeId;
    }
    public void setItmeId(long itmeId) {
        this.itmeId = itmeId;
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
