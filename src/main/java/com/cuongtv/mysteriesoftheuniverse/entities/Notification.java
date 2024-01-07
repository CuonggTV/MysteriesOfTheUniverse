package com.cuongtv.mysteriesoftheuniverse.entities;

public class Notification {
    private int accountSentId;
    private String accountSentName;
    private String details;
    private String avatarName;
    private String url;

    public int getAccountSentId() {
        return accountSentId;
    }

    public void setAccountSentId(int accountSentId) {
        this.accountSentId = accountSentId;
    }

    public String getAccountSentName() {
        return accountSentName;
    }

    public void setAccountSentName(String accountSentName) {
        this.accountSentName = accountSentName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
