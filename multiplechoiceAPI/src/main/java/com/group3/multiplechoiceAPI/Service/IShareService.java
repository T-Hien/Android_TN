package com.group3.multiplechoiceAPI.Service;


import com.group3.multiplechoiceAPI.DTO.Share.Request.ShareDtoRequest;

public interface IShareService {
    boolean shareTopicToUsers(Long topicId, ShareDtoRequest shareDtoRequest, String username);
}
