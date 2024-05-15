package com.group3.multiplechoiceAPI.DTO.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class NotificationResponse {
    private String date;
    private String content;
}
