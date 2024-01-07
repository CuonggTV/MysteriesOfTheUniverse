package com.cuongtv.mysteriesoftheuniverse.entities;

public class Message {
    private int accountSent;
    private int accountReceived;
    private int groupId;
    private String details;
    private String timeSent;

    public int getAccountSent() {
        return accountSent;
    }

    public void setAccountSent(int accountSent) {
        this.accountSent = accountSent;
    }

    public int getAccountReceived() {
        return accountReceived;
    }

    public void setAccountReceived(int accountReceive) {
        this.accountReceived = accountReceive;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }
}
