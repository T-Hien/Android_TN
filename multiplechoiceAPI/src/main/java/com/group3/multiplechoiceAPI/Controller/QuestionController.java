package com.group3.multiplechoiceAPI.Controller;

import com.group3.multiplechoiceAPI.DTO.Question.QuestionConverter;
import com.group3.multiplechoiceAPI.DTO.Question.QuestionDTO;
import com.group3.multiplechoiceAPI.Model.Question;
import com.group3.multiplechoiceAPI.Model.Selection;
import com.group3.multiplechoiceAPI.Service.QuestionService;
import com.group3.multiplechoiceAPI.Service.SelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

    private final QuestionService questionService;
    private final SelectionService selectionService;


    @Autowired
    public QuestionController(QuestionService questionService, SelectionService selectionService) {
        this.questionService = questionService;
        this.selectionService = selectionService;
    }

    @GetMapping(path = "/all")
    public List<QuestionDTO> getAllQuestion(){return questionService.getAllQuestion().stream().map(QuestionConverter::toDTO).collect(Collectors.toList());}

    //Note Back
    @GetMapping(path = "/{topic_set_code}")
    public List<QuestionDTO> getQuestionByTopicSetID(@PathVariable("topic_set_code") Long topicSetID){
        return (List<QuestionDTO>) questionService.getAllQuestionsByTopicSetID(topicSetID).stream().map(QuestionConverter::toDTO).collect(Collectors.toList());
    }
    
    //*************LEVEL***************
    //Note
    @GetMapping(path = "/findQuestion")
    public List<QuestionDTO> getQuestionByTopicSetIDAndLevel(@RequestParam("topicSetID") Long topicSetID,
                                                             @RequestParam("level") int level){
        return (List<QuestionDTO>) questionService.getAllQuestionsByTopicSetIDAndLevel(topicSetID,level).stream().map(QuestionConverter::toDTO).collect(Collectors.toList());
    }
    //Note
    @GetMapping("/levels")
    public List<Integer> getDistinctLevels(@RequestParam("topicSetID") Long topicSetID) {
        return questionService.checkLevel(topicSetID);
    }
}


