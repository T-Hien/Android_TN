package com.group3.multiplechoiceAPI.DTO.Share.Request;

import com.group3.multiplechoiceAPI.DTO.User.Request.UsernameRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShareDtoRequest {
    List<UsernameRequest> users = new ArrayList<>();
}
