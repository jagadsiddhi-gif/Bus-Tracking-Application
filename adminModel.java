package com.example.trackingappliaction2;

public class adminModel {
    private String adminusername,adminpassword,adminemail,adminmobno,adminbranch,userid;
    public adminModel() {
    }

    public adminModel(String adminusername, String adminpassword, String adminemail, String adminmobno, String adminbranch, String userid) {
        this.adminusername = adminusername;
        this.adminpassword = adminpassword;
        this.adminemail = adminemail;
        this.adminmobno = adminmobno;
        this.adminbranch = adminbranch;
        this.userid = userid;
    }

    public String getAdminusername() {
        return adminusername;
    }

    public void setAdminusername(String adminusername) {
        this.adminusername = adminusername;
    }

    public String getAdminpassword() {
        return adminpassword;
    }

    public void setAdminpassword(String adminpassword) {
        this.adminpassword = adminpassword;
    }

    public String getAdminemail() {
        return adminemail;
    }

    public void setAdminemail(String adminemail) {
        this.adminemail = adminemail;
    }

    public String getAdminmobno() {
        return adminmobno;
    }

    public void setAdminmobno(String adminmobno) {
        this.adminmobno = adminmobno;
    }

    public String getAdminbranch() {
        return adminbranch;
    }

    public void setAdminbranch(String adminbranch) {
        this.adminbranch = adminbranch;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
