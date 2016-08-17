package com.movieapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by Javen on 2016/8/3.
 */
public class CategoryModel extends DataSupport implements Parcelable {

    /**
     * categoryid : 40
     * iccon : /image/iccon/new.png
     * mainIccon : /image/iccon/mainIccon/huiyuan.png
     * categoryname : 最近更新
     * orderId : 1
     */
    @Column(unique = true, defaultValue = "unknown")
    private int categoryid;
    private String iccon;
    private String mainIccon;
    private String categoryname;
    private String appId;
    private int orderId;
    private long insertTime;

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getIccon() {
        return iccon;
    }

    public void setIccon(String iccon) {
        this.iccon = iccon;
    }

    public String getMainIccon() {
        return mainIccon;
    }

    public void setMainIccon(String mainIccon) {
        this.mainIccon = mainIccon;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "categoryid=" + categoryid +
                ", iccon='" + iccon + '\'' +
                ", mainIccon='" + mainIccon + '\'' +
                ", categoryname='" + categoryname + '\'' +
                ", appId='" + appId + '\'' +
                ", orderId=" + orderId +
                ", insertTime=" + insertTime +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.categoryid);
        dest.writeString(this.iccon);
        dest.writeString(this.mainIccon);
        dest.writeString(this.categoryname);
        dest.writeString(this.appId);
        dest.writeInt(this.orderId);
        dest.writeLong(this.insertTime);
    }

    public CategoryModel() {
    }

    protected CategoryModel(Parcel in) {
        this.categoryid = in.readInt();
        this.iccon = in.readString();
        this.mainIccon = in.readString();
        this.categoryname = in.readString();
        this.appId = in.readString();
        this.orderId = in.readInt();
        this.insertTime = in.readLong();
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel source) {
            return new CategoryModel(source);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };
}
