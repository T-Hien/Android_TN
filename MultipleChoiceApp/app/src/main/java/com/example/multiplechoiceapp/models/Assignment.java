package com.example.multiplechoiceapp.models;

import java.util.Date;
import java.util.List;

public class Assignment {
    private Long assignmentID;
    private String testDate;
    private Float score;
    private int nb_completedSentences;
    private Float duration;
    private User user;
    private List<Detailed_Assignment> detailedAssignments;

    public Assignment() {
    }

    public Assignment(Long assignmentCode, String testDate, Float score, int nb_completedSentences, Float duration, User user, List<Detailed_Assignment> detailedAssignments) {
        this.assignmentID = assignmentCode;
        this.testDate = testDate;
        this.score = score;
        this.nb_completedSentences = nb_completedSentences;
        this.duration = duration;
        this.user = user;
        this.detailedAssignments = detailedAssignments;
    }

    public Long getAssignmentCode() {
        return assignmentID;
    }

    public void setAssignmentCode(Long assignmentCode) {
        this.assignmentID = assignmentCode;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public int getNb_completedSentences() {
        return nb_completedSentences;
    }

    public void setNb_completedSentences(int nb_completedSentences) {
        this.nb_completedSentences = nb_completedSentences;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Detailed_Assignment> getDetailedAssignments() {
        return detailedAssignments;
    }

    public void setDetailedAssignments(List<Detailed_Assignment> detailedAssignments) {
        this.detailedAssignments = detailedAssignments;
    }
}
