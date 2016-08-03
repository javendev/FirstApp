package com.movieapp.bean;

/**
 * Created by Javen on 2016/7/28.
 */
public class UserModel {

    /**
     * devicemac : d4:6e:5c:90:20:22
     * id : 23
     * IMEI : 867742004717343
     * paystatus : 0
     * validdate : null
     * paytype : null
     * IMSI : 460017219816197
     * userid : 8833865806884238
     * appid : 7e54a1af8f8
     * paydate : null
     * ICCID : 89860114840501879822
     * channelid : null
     * blackstatus : 0
     */

    private String devicemac;
    private int id;
    private String IMEI;
    private String paystatus;
    private Object validdate;
    private Object paytype;
    private String IMSI;
    private String userid;
    private String appid;
    private Object paydate;
    private String ICCID;
    private Object channelid;
    private String blackstatus;

    public String getDevicemac() {
        return devicemac;
    }

    public void setDevicemac(String devicemac) {
        this.devicemac = devicemac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public Object getValiddate() {
        return validdate;
    }

    public void setValiddate(Object validdate) {
        this.validdate = validdate;
    }

    public Object getPaytype() {
        return paytype;
    }

    public void setPaytype(Object paytype) {
        this.paytype = paytype;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Object getPaydate() {
        return paydate;
    }

    public void setPaydate(Object paydate) {
        this.paydate = paydate;
    }

    public String getICCID() {
        return ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public Object getChannelid() {
        return channelid;
    }

    public void setChannelid(Object channelid) {
        this.channelid = channelid;
    }

    public String getBlackstatus() {
        return blackstatus;
    }

    public void setBlackstatus(String blackstatus) {
        this.blackstatus = blackstatus;
    }
}
