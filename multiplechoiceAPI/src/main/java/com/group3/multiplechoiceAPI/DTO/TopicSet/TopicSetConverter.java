package com.group3.multiplechoiceAPI.DTO.TopicSet;

import com.group3.multiplechoiceAPI.Model.Topic_Set;

public class TopicSetConverter {
    public static TopicSetDTO toDTO(Topic_Set topicset) {
        if (topicset == null) return null;

        TopicSetDTO topicsetDTO = new TopicSetDTO();
        topicsetDTO.setTopicSetID(topicset.getTopicSetID());
        topicsetDTO.setDuration(topicset.getDuration());
        topicsetDTO.setCreated(topicset.getCreated());
        topicsetDTO.setTopicSetName(topicset.getTopicSetName());
        topicsetDTO.setTopicID(topicset.getTopic().getTopicID());
        return topicsetDTO;
    }

    /*public static topicset toEntity(topicsetDTO topicsetDTO) {
        if (topicsetDTO == null) return null;

        topicset topicset = new topicset();
        topicset.settopicsetname(topicsetDTO.gettopicsetname());
        topicset.setPassword(topicsetDTO.getPassword());
        topicset.setName(topicsetDTO.getName());
        topicset.setGender(topicsetDTO.getGender());
        topicset.setDayOfBirth(topicsetDTO.getDayOfBirth());
        topicset.setEmail(topicsetDTO.getEmail());
        topicset.setPhoneNumber(topicsetDTO.getPhoneNumber());
        return topicset;
    }*/
}
