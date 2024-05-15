package com.group3.multiplechoiceAPI.DTO.Assignment;

import com.group3.multiplechoiceAPI.Model.Assignment;
import com.group3.multiplechoiceAPI.Model.Question;
import com.group3.multiplechoiceAPI.Model.Topic_Set;
import com.group3.multiplechoiceAPI.Model.User;
import com.group3.multiplechoiceAPI.Service.AssignmentService;
import com.group3.multiplechoiceAPI.Service.TopicService;
import com.group3.multiplechoiceAPI.Service.TopicSetService;
import com.group3.multiplechoiceAPI.Service.UserService;

public class AssignmentConverter {
    private final TopicSetService topicSetService;
    private final UserService userService;

    public AssignmentConverter(TopicSetService topicSetService, UserService userService) {
        this.topicSetService = topicSetService;
        this.userService = userService;
    }

    public static AssignmentDTO toDTO(Assignment assignment) {
        if (assignment == null) return null;

        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setAssignmentID(assignment.getAssignmentID());
        assignmentDTO.setDuration(assignment.getDuration());
        assignmentDTO.setTestDate(assignment.getTestDate());
        assignmentDTO.setUsername(assignment.getUser().getUsername());
        assignmentDTO.setTopicSetID(assignment.getTopicSet().getTopicSetID());
        return assignmentDTO;
    }

    public Assignment toEntity(AssignmentDTO assignmentDTO) {
        if (assignmentDTO == null) return null;

        Assignment assignment = new Assignment();
        assignment.setAssignmentID(assignmentDTO.getAssignmentID());
        assignment.setDuration(assignmentDTO.getDuration());
        assignment.setTestDate(assignmentDTO.getTestDate());
        User user = userService.getUserByUsername(assignmentDTO.getUsername());
        Topic_Set topicSet = topicSetService.getTopicSetByID(assignmentDTO.getTopicSetID());
        assignment.setUser(user);
        assignment.setTopicSet(topicSet);
        return assignment;
    }
}
