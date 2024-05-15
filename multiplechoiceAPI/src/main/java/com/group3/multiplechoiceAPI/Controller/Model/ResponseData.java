package com.group3.multiplechoiceAPI.Controller.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ResponseData {
    private int status;
    private String message;
    private Object data;
    private List<Object> dataList;
}
