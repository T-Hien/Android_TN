package com.group3.multiplechoiceAPI.DTO.TopicSet;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionRequest {

    private String questionContent;

    private String answer;

    private int level;

    private List<SelectionRequest> selection = new ArrayList<>();
}
