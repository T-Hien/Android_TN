package com.group3.multiplechoiceAPI.DTO.TopicSet;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class TopicSetDTO {
    private Long topicSetID;

    private String topicSetName;

    private Date created;

    private Float duration;
    private Long topicID;
}
