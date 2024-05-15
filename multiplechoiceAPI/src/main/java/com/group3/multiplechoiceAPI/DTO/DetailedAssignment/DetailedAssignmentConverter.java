package com.group3.multiplechoiceAPI.DTO.DetailedAssignment;

import com.group3.multiplechoiceAPI.DTO.DetailedAssignmentDTO;
import com.group3.multiplechoiceAPI.DTO.Question.QuestionDTO;
import com.group3.multiplechoiceAPI.Model.*;
import com.group3.multiplechoiceAPI.Service.QuestionService;

public class DetailedAssignmentConverter {

    public static DetailedAssignmentDTO toDTO(Detailed_Assignment detailedAssignment) {
        if (detailedAssignment == null) return null;

        DetailedAssignmentDTO detailedAssignmentDTO = new DetailedAssignmentDTO();
        detailedAssignmentDTO.setQuestionID(detailedAssignment.getQuestion().getQuestionID());
        detailedAssignmentDTO.setAssignmentID(detailedAssignment.getAssignment().getAssignmentID());
        detailedAssignmentDTO.setSelectedAnswer(detailedAssignment.getSelectedAnswer());
        return detailedAssignmentDTO;
    }
}
