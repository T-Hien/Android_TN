package com.example.multiplechoiceapp.models;

public class DetailedAssignmentKey {
    private Long assignmentID;
    private Long questionID;

    public DetailedAssignmentKey(Long assignmentID, Long questionID) {
        this.assignmentID = assignmentID;
        this.questionID = questionID;
    }

    public DetailedAssignmentKey() {
    }

    public Long getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(Long assignmentID) {
        this.assignmentID = assignmentID;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }
}
