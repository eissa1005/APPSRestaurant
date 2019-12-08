package com.example.myrestaurant.Model.Response;

public class Users {
    private String FBID;
    private String name;
    private String userPhone;
    private String userPassword;
    private String address;

    public Users(String FBID, String name, String userPhone, String userPassword, String address) {
        this.FBID = FBID;
        this.name = name;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.address = address;
    }

    public String getFBID() {
        return FBID;
    }

    public void setFBID(String FBID) {
        this.FBID = FBID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
