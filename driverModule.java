package com.example.trackingappliaction2;

public class driverModule {
    private String drivername,drivermail,dbusid,dbusno,driverpassword,userid,docid;
    public driverModule() {

    }

    public driverModule(String drivername, String drivermail, String dbusid, String dbusno, String userid,String driverpassword) {
        this.drivername = drivername;
        this.drivermail = drivermail;
        this.dbusid = dbusid;
        this.dbusno = dbusno;
        this.userid = userid;
        this.driverpassword = driverpassword;
        //this.docid=docid;
    }

   /* public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    */

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getDrivermail() {
        return drivermail;
    }

    public void setDrivermail(String drivermail) {
        this.drivermail = drivermail;
    }

    public String getDbusid() {
        return dbusid;
    }

    public void setDbusid(String dbusid) {
        this.dbusid = dbusid;
    }

    public String getDriverpassword() {
        return driverpassword;
    }

    public void setDriverpassword(String driverpassword) {
        this.driverpassword = driverpassword;
    }

    public String getDbusno() {
        return dbusno;
    }

    public void setDbusno(String dbusno) {
        this.dbusno = dbusno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
