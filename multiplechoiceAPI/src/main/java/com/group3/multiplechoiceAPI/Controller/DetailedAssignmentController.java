package com.group3.multiplechoiceAPI.Controller;

import com.group3.multiplechoiceAPI.Controller.Model.ResponseData;
import com.group3.multiplechoiceAPI.DTO.DetailedAssignment.DetailedAssignmentConverter;
import com.group3.multiplechoiceAPI.DTO.DetailedAssignmentDTO;
import com.group3.multiplechoiceAPI.Model.Detailed_Assignment;
import com.group3.multiplechoiceAPI.Service.DetailedAssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/detailed-assignment")
public class DetailedAssignmentController {
    private final DetailedAssignmentService detailedAssignmentService;

    public DetailedAssignmentController(DetailedAssignmentService detailedAssignmentService) {
        this.detailedAssignmentService = detailedAssignmentService;
    }
    //Note
    @PostMapping(path = "/add")
    @ResponseBody
    public ResponseData addDetailedAssignment(@RequestParam Long assignmentID,
                                      @RequestParam Long questionID,
                                      @RequestParam String selectedAnswer) {
        boolean isSuccess = detailedAssignmentService.addDetailedAssignment(assignmentID, questionID, selectedAnswer);
        ResponseData responseData = new ResponseData();

        if(isSuccess) {
            responseData.setStatus(200);
            responseData.setMessage("Added detailed assignment successfully");
        } else {
            responseData.setStatus(500);
            responseData.setMessage("The assignment ID "+ assignmentID + " or question ID " + questionID + " does not exists!");
        }
        return responseData;
    }
    @GetMapping("/findAssignmentIDAndQuestionID")
    public List<Detailed_Assignment> getDetailedAssignmentsByAssignmentIDAndQuestionID(@RequestParam Long assignmentID, @RequestParam Long questionID) {
        return detailedAssignmentService.findDetailedAssignmentsByAssignmentIDAndQuestionID(assignmentID, questionID);
    }
    //Note
    @GetMapping("/findAssignmentID")
    public List<Detailed_Assignment> getDetailedAssignmentsByAssignmentID(@RequestParam Long assignmentID) {
        return detailedAssignmentService.findDetailedAssignmentsByAssignmentID(assignmentID);
    }
}
