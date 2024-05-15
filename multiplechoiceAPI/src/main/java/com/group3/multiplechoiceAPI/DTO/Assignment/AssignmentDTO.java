package com.group3.multiplechoiceAPI.DTO.Assignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AssignmentDTO {
    private Long assignmentID;
    private Date testDate;
    private Float duration;

    private String username;
    private Long topicSetID;
}
