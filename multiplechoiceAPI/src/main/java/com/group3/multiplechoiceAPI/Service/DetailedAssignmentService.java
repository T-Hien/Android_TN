
package com.group3.multiplechoiceAPI.Service;

import com.group3.multiplechoiceAPI.Model.*;
import com.group3.multiplechoiceAPI.Repository.AssignmentRepository;
import com.group3.multiplechoiceAPI.Repository.DetailedAssignmentRepository;
import com.group3.multiplechoiceAPI.Repository.QuestionRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class DetailedAssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final QuestionRepository questionRepository;
    private final DetailedAssignmentRepository detailedAssignmentRepository;

    public DetailedAssignmentService(AssignmentRepository assignmentRepository, QuestionRepository questionRepository, DetailedAssignmentRepository detailedAssignmentRepository) {
        this.assignmentRepository = assignmentRepository;
        this.questionRepository = questionRepository;
        this.detailedAssignmentRepository = detailedAssignmentRepository;
    }

    public boolean addDetailedAssignment(Long assignmentID, Long questionID, String selectedAnswer) {
        Detailed_Assignment detailedAssignment = new Detailed_Assignment();

        Optional<Assignment> assignment = assignmentRepository.findById(assignmentID);
        if (assignment.isEmpty()) return false;
        detailedAssignment.setAssignment(assignment.get());

        Optional<Question> question = questionRepository.findById(questionID);
        if (question.isEmpty()) return false;
        detailedAssignment.setQuestion(question.get());

        DetailedAssignmentKey detailedAssignmentKey = new DetailedAssignmentKey();
        detailedAssignmentKey.setAssignmentID(assignmentID);
        detailedAssignmentKey.setQuestionID(questionID);

        detailedAssignment.setId(detailedAssignmentKey);
        detailedAssignment.setSelectedAnswer(selectedAnswer);

        detailedAssignmentRepository.save(detailedAssignment);
        return true;
    }
    public List<Detailed_Assignment> findDetailedAssignmentsByAssignmentIDAndQuestionID(Long assignmentID, Long questionID) {
        return detailedAssignmentRepository.findByAssignmentAssignmentIDAndQuestionQuestionID(assignmentID, questionID);
    }

    public List<Detailed_Assignment> findDetailedAssignmentsByAssignmentID(Long assignmentID) {
        return detailedAssignmentRepository.findByAssignmentAssignmentID(assignmentID);
    }

}
