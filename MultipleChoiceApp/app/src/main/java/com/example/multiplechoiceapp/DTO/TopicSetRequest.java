package com.example.multiplechoiceapp.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopicSetRequest {
    private String topicSetName;
    private Float duration;

    private List<QuestionRequest> questions = new ArrayList<>();

    public TopicSetRequest(String topicSetName, Float duration, List<QuestionRequest> selections) {
        this.topicSetName = topicSetName;
        this.duration = duration;
        this.questions = selections;
    }

    public String getTopicSetName() {
        return topicSetName;
    }

    public void setTopicSetName(String topicSetName) {
        this.topicSetName = topicSetName;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public List<QuestionRequest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRequest> questions) {
        this.questions = questions;
    }


}
