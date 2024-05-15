package com.group3.multiplechoiceAPI.DTO.Question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class QuestionDTO {
    private Long topicSetID;

    private Long questionID;
    private String answer;
    private String questionContent;
    private int level;

}
