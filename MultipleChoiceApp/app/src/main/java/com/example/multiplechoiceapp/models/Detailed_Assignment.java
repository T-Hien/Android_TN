package com.example.multiplechoiceapp.models;

import java.util.List;

public class Detailed_Assignment {
    private DetailedAssignmentKey id;
    private Assignment assignment;
    private Question question;
    private String selectedAnswer;

    public Detailed_Assignment() {
    }

    public Detailed_Assignment(DetailedAssignmentKey id, Assignment assignment, Question question, String selectedAnswer) {
        this.id = id;
        this.assignment = assignment;
        this.question = question;
        this.selectedAnswer = selectedAnswer;
    }

    public DetailedAssignmentKey getId() {
        return id;
    }

    public void setId(DetailedAssignmentKey id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
