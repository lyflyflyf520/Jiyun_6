package com.example.mvpdbdemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ItemBean {

    @Id
    private Long id;
    private String mId;
    private String name;
    private String age;
    @Generated(hash = 1702303898)
    public ItemBean(Long id, String mId, String name, String age) {
        this.id = id;
        this.mId = mId;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 95333960)
    public ItemBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMId() {
        return this.mId;
    }
    public void setMId(String mId) {
        this.mId = mId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "ItemBean{" +
                "mId='" + mId + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
