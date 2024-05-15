package com.group3.multiplechoiceAPI.DTO.Topic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TopicDTO {
    private Long topicID;
    private String username;
    private String topicName;
}
