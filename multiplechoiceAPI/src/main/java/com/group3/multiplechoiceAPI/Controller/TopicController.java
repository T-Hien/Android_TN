package com.group3.multiplechoiceAPI.Controller;

import com.group3.multiplechoiceAPI.Controller.Model.ResponseData;
import com.group3.multiplechoiceAPI.DTO.Topic.TopicConverter;
import com.group3.multiplechoiceAPI.DTO.Topic.TopicDTO;
import com.group3.multiplechoiceAPI.DTO.TopicSet.TopicSetConverter;
import com.group3.multiplechoiceAPI.Model.Assignment;
import com.group3.multiplechoiceAPI.Model.Topic;
import com.group3.multiplechoiceAPI.Service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/topic")
public class TopicController {

    private final TopicService topicService;


    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    //Note
    @PostMapping("/{username}/create")
    public ResponseData createTopic(@RequestParam("name") String name, @PathVariable("username") String username){
        Topic topic = topicService.createTopic(username,name);
        ResponseData responseData = new ResponseData();

        responseData.setStatus(200);
        responseData.setMessage("Updated topic successfully");
        responseData.setData(TopicConverter.toDTO(topic));
        return responseData;
    }

    @GetMapping("/all")
    public List<Topic> getAll(){
        return topicService.getAllTopic();
    }

    //******************Update**********s
    //Note
    @GetMapping(path = "/find")
    public List<TopicDTO> getTopicByUsername(@RequestParam("username") String username){
        return topicService.getTopicByUsername(username).stream().map(TopicConverter::toDTO).collect(Collectors.toList());
    }

}
