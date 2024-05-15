package com.example.multiplechoiceapp.models;

import java.util.Date;

public class Share {
    private String username;
    private String topicSetCode;
    private Date sharedDate;
    private String sharedContent;

    private User user;
    private Topic_Set topicSet;

    public Share() {
    }

    public Share(String username, String topicSetCode, Date sharedDate, String sharedContent, User user, Topic_Set topicSet) {
        this.username = username;
        this.topicSetCode = topicSetCode;
        this.sharedDate = sharedDate;
        this.sharedContent = sharedContent;
        this.user = user;
        this.topicSet = topicSet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTopicSetCode() {
        return topicSetCode;
    }

    public void setTopicSetCode(String topicSetCode) {
        this.topicSetCode = topicSetCode;
    }

    public Date getSharedDate() {
        return sharedDate;
    }

    public void setSharedDate(Date sharedDate) {
        this.sharedDate = sharedDate;
    }

    public String getSharedContent() {
        return sharedContent;
    }

    public void setSharedContent(String sharedContent) {
        this.sharedContent = sharedContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic_Set getTopicSet() {
        return topicSet;
    }

    public void setTopicSet(Topic_Set topicSet) {
        this.topicSet = topicSet;
    }
}
