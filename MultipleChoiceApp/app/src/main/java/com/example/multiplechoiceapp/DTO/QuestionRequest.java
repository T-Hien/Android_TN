package com.example.multiplechoiceapp.DTO;



import java.util.ArrayList;
import java.util.List;

public class QuestionRequest {

    private String questionContent;

    private String answer;

    private int level;

    private List<SelectionRequest> selection = new ArrayList<>();

    public QuestionRequest() {
    }

    public QuestionRequest(String questionContent, String answer, List<SelectionRequest> selection) {
        this.questionContent = questionContent;
        this.answer = answer;
        this.selection = selection;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<SelectionRequest> getSelection() {
        return selection;
    }

    public void setSelection(List<SelectionRequest> selection) {
        this.selection = selection;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "QuestionRequest{" +
                "questionContent='" + questionContent + '\'' +
                ", answer='" + answer + '\'' +
                ", selection=" + selection +
                '}';
    }
}

