package com.example.trackingappliaction2;

public class busmodel {
    private String buno,busname,drivermail,longtitude,latitude,custid,userid,realaddress;

    public busmodel() {
    }

    public busmodel(String buno, String busname, String drivermail, String longtitude, String latitude,String custid, String userid) {
        this.buno = buno;
        this.busname = busname;
        this.drivermail = drivermail;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.userid = userid;
        this.custid=custid;
        //this.realaddress=realaddress;
    }

    public String getRealaddress() {
        return realaddress;
    }

    public void setRealaddress(String realaddress) {
        this.realaddress = realaddress;
    }

    public String getBuno() {
        return buno;
    }

    public void setBuno(String buno) {
        this.buno = buno;
    }

    public String getBusname() {
        return busname;
    }

    public void setBusname(String busname) {
        this.busname = busname;
    }

    public String getDrivermail() {
        return drivermail;
    }

    public void setDrivermail(String drivermail) {
        this.drivermail = drivermail;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getCustid() {
        return userid;
    }

    public void setCustid(String userid) {
        this.userid = userid;
    }
}
