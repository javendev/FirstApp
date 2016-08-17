package com.movieapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by Javen on 2016/7/28.
 */
public class UserModel extends DataSupport implements Parcelable {

    /**
     * id : 23
     * devicemac : d4:6e:5c:90:20:22
     * IMEI : 867742004717343
     * IMSI : 460017219816197
     * ICCID : 89860114840501879822
     * userid : 8833865806884238
     * paystatus : 0
     * paytype : 2
     * appid : 7e54a1af8f8
     * paydate : 2016-05-26
     * validdate : 2016-07-06
     * channelid : cp1001
     * blackstatus : 0
     */
    private int id;
    private String devicemac;
    private String IMEI;
    private String IMSI;
    private String ICCID;
    private String userid;
    private String paystatus;
    private String paytype;
    private String appid;
    private String paydate;
    private String validdate;
    private String channelid;
    private String blackstatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevicemac() {
        return devicemac;
    }

    public void setDevicemac(String devicemac) {
        this.devicemac = devicemac;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getICCID() {
        return ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public String getValiddate() {
        return validdate;
    }

    public void setValiddate(String validdate) {
        this.validdate = validdate;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getBlackstatus() {
        return blackstatus;
    }

    public void setBlackstatus(String blackstatus) {
        this.blackstatus = blackstatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.devicemac);
        dest.writeString(this.IMEI);
        dest.writeString(this.IMSI);
        dest.writeString(this.ICCID);
        dest.writeString(this.userid);
        dest.writeString(this.paystatus);
        dest.writeString(this.paytype);
        dest.writeString(this.appid);
        dest.writeString(this.paydate);
        dest.writeString(this.validdate);
        dest.writeString(this.channelid);
        dest.writeString(this.blackstatus);
    }

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.id = in.readInt();
        this.devicemac = in.readString();
        this.IMEI = in.readString();
        this.IMSI = in.readString();
        this.ICCID = in.readString();
        this.userid = in.readString();
        this.paystatus = in.readString();
        this.paytype = in.readString();
        this.appid = in.readString();
        this.paydate = in.readString();
        this.validdate = in.readString();
        this.channelid = in.readString();
        this.blackstatus = in.readString();
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", devicemac='" + devicemac + '\'' +
                ", IMEI='" + IMEI + '\'' +
                ", IMSI='" + IMSI + '\'' +
                ", ICCID='" + ICCID + '\'' +
                ", userid='" + userid + '\'' +
                ", paystatus='" + paystatus + '\'' +
                ", paytype='" + paytype + '\'' +
                ", appid='" + appid + '\'' +
                ", paydate='" + paydate + '\'' +
                ", validdate='" + validdate + '\'' +
                ", channelid='" + channelid + '\'' +
                ", blackstatus='" + blackstatus + '\'' +
                '}';
    }
}
