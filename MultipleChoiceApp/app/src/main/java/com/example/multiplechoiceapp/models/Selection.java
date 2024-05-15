package com.example.multiplechoiceapp.models;

public class Selection {
    private int selectionID;
    private String selectionContent;
    private Question question;

    public Selection() {
    }

    public Selection(int selectionID, String selectionContent, Question question) {
        this.selectionID = selectionID;
        this.selectionContent = selectionContent;
        this.question = question;
    }

    public int getSelectionID() {
        return selectionID;
    }

    public void setSelectionID(int selectionID) {
        this.selectionID = selectionID;
    }

    public String getSelectionContent() {
        return selectionContent;
    }

    public void setSelectionContent(String selectionContent) {
        this.selectionContent = selectionContent;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
