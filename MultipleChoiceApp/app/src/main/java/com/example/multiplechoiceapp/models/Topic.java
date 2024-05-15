package com.example.multiplechoiceapp.models;

import java.util.List;

public class Topic {
    private Long topicID;
    private String topicName;
    private List<Topic_Set> topicSet;
    private User user;

    public Topic() {
    }

    public Topic(Long topicID, String topicName, List<Topic_Set> topicSet, User user) {
        this.topicID = topicID;
        this.topicName = topicName;
        this.topicSet = topicSet;
        this.user = user;
    }

    public Long getTopicCode() {
        return topicID;
    }

    public void setTopicCode(Long topicID) {
        this.topicID= topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<Topic_Set> getTopicSet() {
        return topicSet;
    }

    public void setTopicSet(List<Topic_Set> topicSet) {
        this.topicSet = topicSet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
