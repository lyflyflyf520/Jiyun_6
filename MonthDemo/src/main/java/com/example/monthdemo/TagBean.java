package com.example.monthdemo;

import java.util.List;

public class TagBean {

    /**
     * code : 200
     * ret : success
     * channels : [{"id":1,"name":"头条","channel":"top"},{"id":2,"name":"社会","channel":"shehui"},{"id":3,"name":"国内","channel":"guonei"},{"id":4,"name":"国际","channel":"yule"},{"id":5,"name":"娱乐","channel":"shehui"},{"id":6,"name":"体育","channel":"tiyu"},{"id":7,"name":"军事","channel":"junshi"},{"id":8,"name":"科技","channel":"keji"},{"id":9,"name":"财经","channel":"caijing"},{"id":10,"name":"时尚","channel":"shishang"}]
     */

    private int code;
    private String ret;
    private List<ChannelsBean> channels;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public List<ChannelsBean> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelsBean> channels) {
        this.channels = channels;
    }

    public static class ChannelsBean {
        /**
         * id : 1
         * name : 头条
         * channel : top
         */

        private int id;
        private String name;
        private String channel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }
    }
}
