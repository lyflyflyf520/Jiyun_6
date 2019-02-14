package com.example.demo214;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class T1348647909107Bean {

    /**
     * template : normal1
     * skipID : 00AP0001|2299793
     * lmodify : 2019-02-14 13:25:37
     * postid : PHOT265SH000100A
     * source : 视觉中国
     * title : 新年游客泰山祈福 台阶上硬币扔一地
     * mtime : 2019-02-14 13:25:37
     * hasImg : 1
     * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
     * digest :
     * photosetID : 00AP0001|2299793
     * boardid : photoview_bbs
     * alias : Top News
     * hasAD : 1
     * imgsrc : http://cms-bucket.ws.126.net/2019/02/14/0ba0b8a469e0408cbc08a573a346445b.jpeg
     * ptime : 2019-02-14 10:48:08
     * daynum : 17941
     * hasHead : 1
     * imgType : 1
     * order : 1
     * editor : []
     * votecount : 375
     * hasCover : false
     * docid : 9IG74V5H00963VRO_E7VIRBRLbjxiaoqi1updateDoc
     * tname : 头条
     * priority : 666
     * ads : [{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2299796","tag":"photoset","title":"新一轮中美经贸高级别磋商在京开幕","imgsrc":"bigimg","url":"00AN0001|2299796"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2299795","tag":"photoset","title":"全国多地情侣情人节扎堆领证","imgsrc":"bigimg","url":"00AP0001|2299795"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2299794","tag":"photoset","title":"华北迎来今冬来最强降雪","imgsrc":"bigimg","url":"00AN0001|2299794"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2299793","tag":"photoset","title":"游客泰山祈福 台阶上硬币零钱扔一地","imgsrc":"bigimg","url":"00AP0001|2299793"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2299790","tag":"photoset","title":"北京情人节鲜花市场销售火热 ","imgsrc":"bigimg","url":"00AP0001|2299790"}]
     * ename : androidnews
     * replyCount : 417
     * imgsum : 4
     * hasIcon : false
     * skipType : photoset
     * cid : C1348646712614
     */

    @Id
    private Long lid;

    private String template;
    private String skipID;
    private String lmodify;
    private String postid;
    private String source;
    private String title;
    private String mtime;
    private int hasImg;
    private String topic_background;
    private String digest;
    private String photosetID;
    private String boardid;
    private String alias;
    private int hasAD;
    private String imgsrc;
    private String ptime;
    private String daynum;
    private int hasHead;
    private int imgType;
    private int order;
    private int votecount;
    private boolean hasCover;
    private String docid;
    private String tname;
    private int priority;
    private String ename;
    private int replyCount;
    private int imgsum;
    private boolean hasIcon;
    private String skipType;
    private String cid;
    @Transient
    private List<?> editor;
    @Transient
    private List<AdsBean> ads;

    @Generated(hash = 1010013540)
    public T1348647909107Bean(Long lid, String template, String skipID, String lmodify, String postid, String source, String title, String mtime, int hasImg, String topic_background, String digest, String photosetID, String boardid, String alias, int hasAD, String imgsrc, String ptime, String daynum, int hasHead, int imgType, int order, int votecount, boolean hasCover, String docid, String tname, int priority, String ename, int replyCount, int imgsum, boolean hasIcon, String skipType, String cid) {
        this.lid = lid;
        this.template = template;
        this.skipID = skipID;
        this.lmodify = lmodify;
        this.postid = postid;
        this.source = source;
        this.title = title;
        this.mtime = mtime;
        this.hasImg = hasImg;
        this.topic_background = topic_background;
        this.digest = digest;
        this.photosetID = photosetID;
        this.boardid = boardid;
        this.alias = alias;
        this.hasAD = hasAD;
        this.imgsrc = imgsrc;
        this.ptime = ptime;
        this.daynum = daynum;
        this.hasHead = hasHead;
        this.imgType = imgType;
        this.order = order;
        this.votecount = votecount;
        this.hasCover = hasCover;
        this.docid = docid;
        this.tname = tname;
        this.priority = priority;
        this.ename = ename;
        this.replyCount = replyCount;
        this.imgsum = imgsum;
        this.hasIcon = hasIcon;
        this.skipType = skipType;
        this.cid = cid;
    }

    @Generated(hash = 317392173)
    public T1348647909107Bean() {
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSkipID() {
        return skipID;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public String getTopic_background() {
        return topic_background;
    }

    public void setTopic_background(String topic_background) {
        this.topic_background = topic_background;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getPhotosetID() {
        return photosetID;
    }

    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getHasAD() {
        return hasAD;
    }

    public void setHasAD(int hasAD) {
        this.hasAD = hasAD;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getDaynum() {
        return daynum;
    }

    public void setDaynum(String daynum) {
        this.daynum = daynum;
    }

    public int getHasHead() {
        return hasHead;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public int getImgType() {
        return imgType;
    }

    public void setImgType(int imgType) {
        this.imgType = imgType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public boolean isHasCover() {
        return hasCover;
    }

    public void setHasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getImgsum() {
        return imgsum;
    }

    public void setImgsum(int imgsum) {
        this.imgsum = imgsum;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<?> getEditor() {
        return editor;
    }

    public void setEditor(List<?> editor) {
        this.editor = editor;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public Long getLid() {
        return this.lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public boolean getHasCover() {
        return this.hasCover;
    }

    public boolean getHasIcon() {
        return this.hasIcon;
    }

    public static class AdsBean {
        /**
         * subtitle :
         * skipType : photoset
         * skipID : 00AN0001|2299796
         * tag : photoset
         * title : 新一轮中美经贸高级别磋商在京开幕
         * imgsrc : bigimg
         * url : 00AN0001|2299796
         */

        private String subtitle;
        private String skipType;
        private String skipID;
        private String tag;
        private String title;
        private String imgsrc;
        private String url;

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
