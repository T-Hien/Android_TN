package com.group3.multiplechoiceAPI.Repository;

import com.group3.multiplechoiceAPI.Model.Assignment;
import com.group3.multiplechoiceAPI.Model.Question;
import com.group3.multiplechoiceAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("SELECT q FROM Assignment q WHERE q.topicSet.topicSetID = :topicSetID AND q.user.username = :username ORDER BY q.assignmentID DESC LIMIT 1")
    List<Assignment> findAllTopicSetByTopicSetIDAndUsername(@Param("topicSetID") Long topicSetID, @Param("username") String username);
}
