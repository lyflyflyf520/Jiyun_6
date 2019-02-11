package com.example.mvp_34;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DataBean {


    @Id
    private Long lid;

    private String id;
    private String title;
    private String pic;
    private String collect_num;
    private String food_str;
    private int num;

    @Generated(hash = 766283254)
    public DataBean(Long lid, String id, String title, String pic,
            String collect_num, String food_str, int num) {
        this.lid = lid;
        this.id = id;
        this.title = title;
        this.pic = pic;
        this.collect_num = collect_num;
        this.food_str = food_str;
        this.num = num;
    }

    @Generated(hash = 908697775)
    public DataBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }

    public String getFood_str() {
        return food_str;
    }

    public void setFood_str(String food_str) {
        this.food_str = food_str;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getLid() {
        return this.lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }
}
