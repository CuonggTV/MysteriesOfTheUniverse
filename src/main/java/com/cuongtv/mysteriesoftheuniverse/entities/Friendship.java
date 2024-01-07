package com.cuongtv.mysteriesoftheuniverse.entities;

public class Friendship {
    private int accountRequest;
    private int accountReceived;
    private String accountRequestName;
    private String introduction;
    private boolean isAccepted;

    public int getAccountRequest() {
        return accountRequest;
    }

    public void setAccountRequest(int accountRequest) {
        this.accountRequest = accountRequest;
    }

    public int getAccountReceived() {
        return accountReceived;
    }

    public void setAccountReceived(int accountReceived) {
        this.accountReceived = accountReceived;
    }

    public String getAccountRequestName() {
        return accountRequestName;
    }

    public void setAccountRequestName(String accountRequestName) {
        this.accountRequestName = accountRequestName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
