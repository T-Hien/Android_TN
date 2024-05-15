package com.group3.multiplechoiceAPI.Service;

import com.group3.multiplechoiceAPI.Model.Assignment;
import com.group3.multiplechoiceAPI.Model.Topic_Set;
import com.group3.multiplechoiceAPI.Model.User;
import com.group3.multiplechoiceAPI.Repository.AssignmentRepository;
import com.group3.multiplechoiceAPI.Repository.TopicSetRepository;
import com.group3.multiplechoiceAPI.Repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class AssignmentService {
    private final UserRepository userRepository;
    private final TopicSetRepository topicSetRepository;
    private final AssignmentRepository assignmentRepository;


    public AssignmentService(UserRepository userRepository, TopicSetRepository topicSetRepository, AssignmentRepository assignmentRepository) {
        this.userRepository = userRepository;
        this.topicSetRepository = topicSetRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public long addAssignment(float duration, Long topicSetID, String username) {
        Assignment assignment = new Assignment();
        Date currentDate = new Date();

        assignment.setTestDate(currentDate);
        assignment.setDuration(duration);

        Optional<Topic_Set> topicSet = topicSetRepository.findById(topicSetID);
        if (topicSet.isEmpty()) return -1; // Hoặc mã lỗi khác
        assignment.setTopicSet(topicSet.get());

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) return -1; // Hoặc mã lỗi khác
        assignment.setUser(user.get());

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return savedAssignment.getAssignmentID();
    }


    public List<Assignment> getAllAssignmentByTopicSetIDAndUsername(Long topicSetID, String username) {
        return assignmentRepository.findAllTopicSetByTopicSetIDAndUsername(topicSetID,username);
    }
    public long AssignmentCount(){
        return assignmentRepository.count();
    }
}

