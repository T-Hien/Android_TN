package com.group3.multiplechoiceAPI.DTO.TopicSet;


import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TopicSetResponse {
    private Long topicSetCode;

    private String topicSetName;

    private Date created;

    private float duration;

    private TopicResponse topic;

    private List<QuestionResponse> questions = new ArrayList<>();
}
