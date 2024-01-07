package com.cuongtv.mysteriesoftheuniverse.entities;

import com.cuongtv.mysteriesoftheuniverse.dao.PostDao;
import com.cuongtv.mysteriesoftheuniverse.dao.PostLikeDao;

import java.util.List;

public class Post {
    private int id;
    private int accountId;
    private String avatarName;
    private String username;
    private int groupId;
    private String groupName;
    private String visibility;
    private String timeSent;
    private String details;
    private String imageName;
    private int likes;
    private boolean isLiked;
    private List<Comment> commentList;
    private int totalComment ;
    private boolean isClickComment;
    private boolean favorite;


    public Post() {
    }

    public Post(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getLikes() {
        return PostLikeDao.getPostLike(id);
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }


    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getTotalComment() {
        return commentList.size();
    }

    public boolean getIsClickComment() {
        return isClickComment;
    }

    public void setClickComment(boolean clickComment) {
        this.isClickComment = clickComment;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", groupName='" + groupName + '\'' +
                ", timeSent='" + timeSent + '\'' +
                ", details='" + details + '\'' +
                ", imagePath='" + imageName + '\'' +
                '}';
    }

}
