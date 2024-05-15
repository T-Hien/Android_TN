package com.group3.multiplechoiceAPI.DTO.TopicSet;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TopicSetRequest {
    private Long topicSetCode;

    private String topicSetName;

    private Date created;

    private float duration;

    private List<QuestionRequest> questions = new ArrayList<>();
}
