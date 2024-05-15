package com.group3.multiplechoiceAPI.DTO.User;

import com.group3.multiplechoiceAPI.Model.Share;
import com.group3.multiplechoiceAPI.Model.User;
import com.group3.multiplechoiceAPI.Repository.AssignmentRepository;
import com.group3.multiplechoiceAPI.Repository.ShareRepository;
import com.group3.multiplechoiceAPI.Repository.TopicRepository;
import com.group3.multiplechoiceAPI.Repository.UserRepository;
import com.group3.multiplechoiceAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserConverter {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setName(user.getName());
        userDTO.setGender(user.getGender());
        userDTO.setDayOfBirth(user.getDayOfBirth());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setGender(userDTO.getGender());
        user.setDayOfBirth(userDTO.getDayOfBirth());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
}
