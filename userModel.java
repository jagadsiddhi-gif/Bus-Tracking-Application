package com.example.trackingappliaction2;

public class userModel {
    private String username,userpassword,usernumber,usermail,userid;

    public userModel() {
    }

    public userModel(String username, String userpassword, String usermail,String usernumber, String userid) {
        this.username = username;
        this.userpassword = userpassword;
        this.usermail = usermail;
        this.userid = userid;
        this.usernumber=usernumber;
    }
    //make getter and setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }
}
