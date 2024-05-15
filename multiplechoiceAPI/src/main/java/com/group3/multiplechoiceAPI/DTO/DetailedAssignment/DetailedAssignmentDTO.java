package com.group3.multiplechoiceAPI.DTO;

import com.group3.multiplechoiceAPI.DTO.Question.QuestionDTO;
import com.group3.multiplechoiceAPI.Model.Assignment;
import com.group3.multiplechoiceAPI.Model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetailedAssignmentDTO {
    private Long assignmentID;
    private Long questionID;
    private String selectedAnswer;
}
