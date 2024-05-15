package com.group3.multiplechoiceAPI.Controller;

import com.group3.multiplechoiceAPI.DTO.Share.Request.SharedTopicSetResponse;
import com.group3.multiplechoiceAPI.DTO.TopicSet.TopicSetConverter;
import com.group3.multiplechoiceAPI.DTO.TopicSet.TopicSetDTO;
import com.group3.multiplechoiceAPI.DTO.TopicSet.TopicSetRequest;
import com.group3.multiplechoiceAPI.DTO.TopicSet.TopicSetResponse;
import com.group3.multiplechoiceAPI.DTO.User.UserConverter;
import com.group3.multiplechoiceAPI.Model.Topic_Set;
import com.group3.multiplechoiceAPI.Service.TopicSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/topic_set")
public class TopicSetController {

    private final TopicSetService topicSetService;



    @Autowired
    public TopicSetController(TopicSetService topicSetService) {
        this.topicSetService = topicSetService;
    }


    @GetMapping("/all")
    public List<TopicSetDTO> getAllTopicSet(){
        return topicSetService.getAllTopicSet().stream().map(TopicSetConverter::toDTO).collect(Collectors.toList());
    }

    //Note
    @GetMapping("/{username}/shared")
    public List<SharedTopicSetResponse> getShareTopicOfUser(@PathVariable("username") String username){
        return topicSetService.getShareTopicOfUer(username);
    }

    //Note
    @GetMapping("/topicID")
    public List<TopicSetDTO> getAllTopicSetByTopicID(@RequestParam("topicID") Long topicID){
        return topicSetService.getTopicSetByTopicID(topicID).stream().map(TopicSetConverter::toDTO).collect(Collectors.toList());
    }

    //Note
    @PostMapping("/add/{topicId}")
    public ResponseEntity<TopicSetResponse> createTopicSetToTopic(@PathVariable(name = "topicId") Long topicId, @RequestBody TopicSetRequest topicSet){
        TopicSetResponse response = topicSetService.createTopicSetToTopic(topicId,topicSet);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
