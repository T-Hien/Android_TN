package com.group3.multiplechoiceAPI.Repository;

import com.group3.multiplechoiceAPI.Model.DetailedAssignmentKey;
import com.group3.multiplechoiceAPI.Model.Detailed_Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetailedAssignmentRepository extends JpaRepository<Detailed_Assignment, DetailedAssignmentKey> {
    List<Detailed_Assignment> findByAssignmentAssignmentIDAndQuestionQuestionID(Long assignmentID, Long questionID);

    List<Detailed_Assignment> findByAssignmentAssignmentID(Long assignmentID);
}
