package com.group3.multiplechoiceAPI.Controller;

import com.group3.multiplechoiceAPI.DTO.Share.Request.ShareDtoRequest;
import com.group3.multiplechoiceAPI.Service.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/share")
public class ShareController {
    @Autowired
    private IShareService shareService;
    //Note
    @PostMapping("{topicId}/topic-set/{topic-setId}/{username}")
    public ResponseEntity<Boolean> shareTopicToUsers(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "topic-setId") Long topicsetId,
            @RequestBody ShareDtoRequest shareDtoRequest){
        boolean message = shareService.shareTopicToUsers(topicsetId, shareDtoRequest,username);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}
