package com.turbur.entity;

import java.util.Date;

public class User {
    private Integer userId;

    private String userName;

    private String password;

    private Integer credits;

    private Date lastVisit;

    private String lastIp;

    public User(Integer userId, String userName, String password, Integer credits, Date lastVisit, String lastIp) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.credits = credits;
        this.lastVisit = lastVisit;
        this.lastIp = lastIp;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }
}