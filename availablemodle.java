package com.example.trackingappliaction2;

public class availablemodle {

    String busid,busno,fulladdress,userid,docid;

    public availablemodle() {
    }

    public availablemodle(String busid, String busno, String fulladdress, String userid,String docid) {
        this.busid = busid;
        this.busno = busno;
        this.fulladdress = fulladdress;
        this.userid = userid;
        this.docid=docid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
