package com.group3.multiplechoiceAPI.Service;

import com.group3.multiplechoiceAPI.DTO.Topic.TopicDTO;
import com.group3.multiplechoiceAPI.Model.Topic;
import com.group3.multiplechoiceAPI.Model.User;
import com.group3.multiplechoiceAPI.Repository.TopicRepository;
import com.group3.multiplechoiceAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicService {
    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }
    public List<Topic> getAllTopic(){
        return topicRepository.findAll();
    }
    public List<Topic> getTopicByUsername(String username){
        return topicRepository.findAllTopicByUsername(username);
    }

    public Topic createTopic(String username, String name) {
        Topic topic = new Topic(name);
        User user = userRepository.findByUsername(username).orElse(null);
        topic.setUser(user);
        user.getTopicList().add(topic);
        user = userRepository.save(user);
        return user.getTopicList().get(user.getTopicList().size() - 1);
    }
}
