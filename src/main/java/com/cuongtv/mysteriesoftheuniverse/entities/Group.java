package com.cuongtv.mysteriesoftheuniverse.entities;

public class Group {
    private int id;
    private int accountOwner;
    private String accountName;
    private String name;
    private String details;
    private String dateCreated;
    private boolean approve;

    public int getId() {
        return id;
    }

    public void setId(int groupId) {
        this.id = groupId;
    }

    public int getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(int accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean getApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }
}
