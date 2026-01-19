package com.example.trackingappliaction2;

public class newbusmodel {
    String busid,busname,userid,busno,latitude,longitude,custid;

    public newbusmodel() {
    }

    public newbusmodel(String busid, String busname, String userid, String latitude, String longitude, String custid) {
        this.busid = busid;
        this.busname = busname;
        this.userid = userid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.custid = custid;
        //this.busno=busno;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getBusname() {
        return busname;
    }

    public void setBusname(String busname) {
        this.busname = busname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }
}
