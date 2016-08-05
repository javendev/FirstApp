package com.movieapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by Javen on 2016/7/25.
 */
public class MovieModel extends DataSupport implements Parcelable {

    /**
     * videoid : 2
     * title : 愤怒的小鸟
     * description : 手游改编，全程笑点密集
     * playerpiclink : /image/fenlei/zuijingenxin/bird.png
     * fenleiLink : /image/fenlei/zuijingenxin/bird.png
     * svideolink : yyy
     * videolink : http://192.168.111.245:8080/movie/angryBird.mp4
     * updatedate : 2016-07-03 14:46:34
     * orderId : 7
     * piclink : /image/recent/bird.png
     * buyamount : 10
     * favorcount : 35
     * viewcount : 20
     * status : 1
     * categoryid : 40
     */

    private int videoid;
    private String title;
    private String description;
    private String playerpiclink;
    private String fenleiLink;
    private String svideolink;
    private String videolink;
    private String updatedate;
    private int orderId;
    private String piclink;
    private String buyamount;
    private int favorcount;
    private int viewcount;
    private String status;
    private String categoryid;
    private int rankType;

    public MovieModel(String title, String videolink, String description, String fenleiLink) {
        this.title = title;
        this.description = description;
        this.fenleiLink = fenleiLink;
        this.videolink = videolink;
    }

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlayerpiclink() {
        return playerpiclink;
    }

    public void setPlayerpiclink(String playerpiclink) {
        this.playerpiclink = playerpiclink;
    }

    public String getFenleiLink() {
        return fenleiLink;
    }

    public void setFenleiLink(String fenleiLink) {
        this.fenleiLink = fenleiLink;
    }

    public String getSvideolink() {
        return svideolink;
    }

    public void setSvideolink(String svideolink) {
        this.svideolink = svideolink;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPiclink() {
        return piclink;
    }

    public void setPiclink(String piclink) {
        this.piclink = piclink;
    }

    public String getBuyamount() {
        return buyamount;
    }

    public void setBuyamount(String buyamount) {
        this.buyamount = buyamount;
    }

    public int getFavorcount() {
        return favorcount;
    }

    public void setFavorcount(int favorcount) {
        this.favorcount = favorcount;
    }

    public int getViewcount() {
        return viewcount;
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public int getRankType() {
        return rankType;
    }

    public void setRankType(int rankType) {
        this.rankType = rankType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.videoid);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.playerpiclink);
        dest.writeString(this.fenleiLink);
        dest.writeString(this.svideolink);
        dest.writeString(this.videolink);
        dest.writeString(this.updatedate);
        dest.writeInt(this.orderId);
        dest.writeString(this.piclink);
        dest.writeString(this.buyamount);
        dest.writeInt(this.favorcount);
        dest.writeInt(this.viewcount);
        dest.writeString(this.status);
        dest.writeString(this.categoryid);
        dest.writeInt(this.rankType);
    }

    public MovieModel() {
    }

    protected MovieModel(Parcel in) {
        this.videoid = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.playerpiclink = in.readString();
        this.fenleiLink = in.readString();
        this.svideolink = in.readString();
        this.videolink = in.readString();
        this.updatedate = in.readString();
        this.orderId = in.readInt();
        this.piclink = in.readString();
        this.buyamount = in.readString();
        this.favorcount = in.readInt();
        this.viewcount = in.readInt();
        this.status = in.readString();
        this.categoryid = in.readString();
        this.rankType = in.readInt();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public String toString() {
        return "MovieModel{" +
                "videoid=" + videoid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", playerpiclink='" + playerpiclink + '\'' +
                ", fenleiLink='" + fenleiLink + '\'' +
                ", svideolink='" + svideolink + '\'' +
                ", videolink='" + videolink + '\'' +
                ", updatedate='" + updatedate + '\'' +
                ", orderId=" + orderId +
                ", piclink='" + piclink + '\'' +
                ", buyamount='" + buyamount + '\'' +
                ", favorcount=" + favorcount +
                ", viewcount=" + viewcount +
                ", status='" + status + '\'' +
                ", categoryid='" + categoryid + '\'' +
                ", rankType='" + rankType + '\'' +
                '}';
    }
}
