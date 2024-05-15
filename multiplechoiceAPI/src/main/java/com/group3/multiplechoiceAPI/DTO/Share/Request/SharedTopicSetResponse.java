package com.group3.multiplechoiceAPI.DTO.Share.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedTopicSetResponse {
    private Long topicSetId;
    private String name;
    private String username;
    private Float duration;
}
