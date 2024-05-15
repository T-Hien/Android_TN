package com.group3.multiplechoiceAPI.DTO.TopicSet;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionResponse {
    private Long questionCode;

    private String questionContent;

    private String answer;

    private List<SelectionResponse> selections = new ArrayList<>();
}
