package com.group3.multiplechoiceAPI.DTO.Topic;

import com.group3.multiplechoiceAPI.Model.Topic;
import com.group3.multiplechoiceAPI.Model.Topic_Set;

public class TopicConverter {
    public static TopicDTO toDTO(Topic topic) {
        if (topic == null) return null;

        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setTopicID(topic.getTopicID());
        topicDTO.setUsername(topic.getUser().getUsername());
        topicDTO.setTopicName(topic.getTopicName());
        return topicDTO;
    }

    /*public static topic toEntity(topicDTO topicDTO) {
        if (topicDTO == null) return null;

        topic topic = new topic();
        topic.settopicname(topicDTO.gettopicname());
        topic.setPassword(topicDTO.getPassword());
        topic.setName(topicDTO.getName());
        topic.setGender(topicDTO.getGender());
        topic.setDayOfBirth(topicDTO.getDayOfBirth());
        topic.setEmail(topicDTO.getEmail());
        topic.setPhoneNumber(topicDTO.getPhoneNumber());
        return topic;
    }*/
}
