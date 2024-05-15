package com.example.multiplechoiceapp.models;


import java.util.Date;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String name;
    private int gender;
    private Date dayOfBirth;
    private String email;
    private String phoneNumber;
    private List<Share> shareList;
    private List<Topic_Set> topicList;
    private List<Assignment> assignmentList;

    public User() {
    }

    public User(String username, String password, String name, int gender, Date dayOfBirth, String email, String phoneNumber, List<Share> shareList, List<Topic_Set> topicList, List<Assignment> assignmentList) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shareList = shareList;
        this.topicList = topicList;
        this.assignmentList = assignmentList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Share> getShareList() {
        return shareList;
    }

    public void setShareList(List<Share> shareList) {
        this.shareList = shareList;
    }

    public List<Topic_Set> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic_Set> topicList) {
        this.topicList = topicList;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }
}
