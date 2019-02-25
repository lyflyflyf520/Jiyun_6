package com.example.monthdemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class WarBean {

    /**
     * template : recommend
     * skipID : 4T8E0001|2299934
     * lmodify : 2019-02-21 09:53:17
     * postid : PHOT2660U00014T8
     * source : 参考消息网
     * title : 不差钱!美军吊运AH-1Z武直当靶标
     * mtime : 2019-02-21 09:53:17
     * hasImg : 1
     * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348647991705.jpg
     * digest :
     * photosetID : 4T8E0001|2299934
     * boardid : photoview_bbs
     * alias : Military
     * hasAD : 1
     * imgsrc : http://pic-bucket.ws.126.net/photo/0001/2019-02-21/E8HG5IL64T8E0001NOS.jpg
     * ptime : 2019-02-21 09:50:00
     * daynum : 17948
     * hasHead : 1
     * order : 1
     * votecount : 884
     * hasCover : false
     * docid : 9IG74V5H00963VRO_0001set2299934updateDoc
     * tname : 军事
     * priority : 99
     * ename : junshi
     * replyCount : 1230
     * imgsum : 9
     * hasIcon : true
     * skipType : photoset
     * cid : C1348647991705
     */
    @Id
    private Long id;
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

    @Generated(hash = 499958848)
    public WarBean(Long id, String template, String skipID, String lmodify, String postid,
            String source, String title, String mtime, int hasImg, String topic_background,
            String digest, String photosetID, String boardid, String alias, int hasAD, String imgsrc,
            String ptime, String daynum, int hasHead, int order, int votecount, boolean hasCover,
            String docid, String tname, int priority, String ename, int replyCount, int imgsum,
            boolean hasIcon, String skipType, String cid) {
        this.id = id;
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

    @Generated(hash = 1454364269)
    public WarBean() {
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getHasCover() {
        return this.hasCover;
    }

    public boolean getHasIcon() {
        return this.hasIcon;
    }
}
