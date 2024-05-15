package com.example.multiplechoiceapp.DTO;

public class SharedTopicSetResponse {
    private Long topicSetId;
    private String name;
    private String username;
    private Float duration;

    public SharedTopicSetResponse(Long topicSetId, String name, String username, Float duration) {
        this.topicSetId = topicSetId;
        this.name = name;
        this.username = username;
        this.duration = duration;
    }

    public Long getTopicSetId() {
        return topicSetId;
    }

    public void setTopicSetId(Long topicSetId) {
        this.topicSetId = topicSetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }
}
