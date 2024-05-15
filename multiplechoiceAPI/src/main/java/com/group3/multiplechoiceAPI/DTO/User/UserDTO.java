package com.group3.multiplechoiceAPI.DTO.User;

import com.group3.multiplechoiceAPI.Model.Assignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private int gender;
    private Date dayOfBirth;
    private String email;
    private String phoneNumber;
}
