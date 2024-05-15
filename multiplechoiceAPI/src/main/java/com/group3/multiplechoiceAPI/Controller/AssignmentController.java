package com.group3.multiplechoiceAPI.Controller;

import com.group3.multiplechoiceAPI.Controller.Model.ResponseData;
import com.group3.multiplechoiceAPI.DTO.Assignment.AssignmentConverter;
import com.group3.multiplechoiceAPI.DTO.Assignment.AssignmentDTO;
import com.group3.multiplechoiceAPI.DTO.Question.QuestionConverter;
import com.group3.multiplechoiceAPI.Model.Assignment;
import com.group3.multiplechoiceAPI.Service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping(path = "/count")
    public long assignmentCount(){
        return assignmentService.AssignmentCount();
    }

    //Note
    @PostMapping(path = "/add")
    public ResponseEntity<ResponseData> addAssignment(@RequestParam float duration,
                                                      @RequestParam Long topicSetID,
                                                      @RequestParam String username) {
        Long assignmentID = assignmentService.addAssignment(duration, topicSetID, username);
        if (assignmentID != null && assignmentID > 0) {
            ResponseData responseData = new ResponseData();
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setMessage("Added assignment successfully");
            responseData.setData(assignmentID);
            return ResponseEntity.ok(responseData);
        } else {

            ResponseData responseData = new ResponseData();
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setMessage("The username " + username + " or topic set ID " + topicSetID + " does not exist!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }
    //*********************Update**************
    //Note
    @GetMapping(path = "/find")
    public List<AssignmentDTO> getAssignmentByTopicSetIDAndUsername(@RequestParam("topicSetID") Long topicSetID,
                                                     @RequestParam("username") String username){
        return (List<AssignmentDTO>) assignmentService.getAllAssignmentByTopicSetIDAndUsername(topicSetID,username).stream().map(AssignmentConverter::toDTO).collect(Collectors.toList());
    }
}
