package com.example.day10_eventbus;

/**
 * 事件对象的创建
 */
public class MsgEvent {


    public MsgEvent(int id, String content) {
        this.id = id;
        this.content = content;
    }

    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
