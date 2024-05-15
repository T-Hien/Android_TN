package com.group3.multiplechoiceAPI.Controller;

import com.group3.multiplechoiceAPI.Controller.Model.ResponseData;
import com.group3.multiplechoiceAPI.DTO.Notification.NotificationResponse;
import com.group3.multiplechoiceAPI.DTO.User.Response.UserDtoResponse;
import com.group3.multiplechoiceAPI.DTO.User.UserConverter;
import com.group3.multiplechoiceAPI.DTO.User.UserDTO;
import com.group3.multiplechoiceAPI.Model.User;
import com.group3.multiplechoiceAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/all")
    public ResponseData getAllUsers() {
        List<UserDTO> userDTOList = userService.getAllStudents().stream().map(UserConverter::toDTO).collect(Collectors.toList());
        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setMessage("Get all users successfully");
        responseData.setDataList(Collections.singletonList(userDTOList));
        return responseData;
    }

    //Note
    @GetMapping(path = "/{username}")
    public ResponseData getUser(@PathVariable("username") String username) {
        ResponseData responseData = new ResponseData();
        User user = userService.getUserByUsername(username);
        if (user != null) {
            responseData.setStatus(200);
            responseData.setMessage("Get user successfully");
            responseData.setData(UserConverter.toDTO(user));
        } else {
            responseData.setStatus(500);
            responseData.setMessage("The username "+ username + " doesn't exists!");
        }

        return  responseData;
    }

    //Note
    @PostMapping(path = "/account")
    @ResponseBody
    public ResponseData addAccount(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String phoneNumber) {
        boolean isSuccess = userService.addUser(username, password, phoneNumber);
        ResponseData responseData = new ResponseData();

        if(isSuccess) {
            responseData.setStatus(200);
            responseData.setMessage("Added account successfully");
        } else {
            responseData.setStatus(500);
            responseData.setMessage("The username "+ username + " does exists!");
        }

        return responseData;
    }

    @PutMapping(path = "/{username}")
    @ResponseBody
    public ResponseData updateUser(@RequestBody User user) {
        boolean isSuccess = userService.updateUser(user);

        ResponseData responseData = new ResponseData();
        if(isSuccess) {
            responseData.setStatus(200);
            responseData.setMessage("Updated user successfully");
        } else {
            responseData.setStatus(500);
            responseData.setMessage("The username " + user.getUsername() + " does not exists!");
        }

        return responseData;
    }

    @DeleteMapping(path = "/{username}")
    @ResponseBody
    public ResponseData deleteUser(@PathVariable("username") String username) {
        boolean isSuccess = userService.deleteUser(username);

        ResponseData responseData = new ResponseData();
        if(isSuccess) {
            responseData.setStatus(200);
            responseData.setMessage("Deleted user successfully");
        } else {
            responseData.setStatus(500);
            responseData.setMessage("The username " + username + " does not exists!");
        }

        return responseData;
    }
    //Note
    @PostMapping(path = "/sign-in")
    @ResponseBody
    public ResponseData signIn(
            @RequestParam String username,
            @RequestParam String password) {
        boolean isSuccess = userService.signIn(username, password);

        ResponseData responseData = new ResponseData();
        if (isSuccess) {
            responseData.setStatus(200);
            responseData.setMessage("Sign in successfully");
        }
        else {
            responseData.setStatus(500);
            responseData.setMessage("Wrong username or password!");
        }

        return responseData;
    }

    //Note
    @PutMapping(path = "/account")
    @ResponseBody
    public ResponseData resetPassword(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String phoneNumber) {
        boolean isSuccess = userService.resetPassword(username, password, phoneNumber);

        ResponseData responseData = new ResponseData();
        if (isSuccess) {
            responseData.setStatus(200);
            responseData.setMessage("Reset password successfully!");
        }
        else {
            responseData.setStatus(500);
            responseData.setMessage("Username or phone number is wrong!");
        }

        return responseData;
    }
    //Note
    @GetMapping(path = "/tested-assignment/{username}")
    public ResponseData getTestByUsername(@PathVariable("username") String username) {
        ResponseData responseData = new ResponseData();
        List<Object> testedAssignment = userService.getTestByUsername(username);
        if (testedAssignment != null) {
            responseData.setStatus(200);
            responseData.setMessage("Get user successfully");
            responseData.setDataList(testedAssignment);
        } else {
            responseData.setStatus(500);
            responseData.setMessage("The tested assignment of "+ username + " doesn't exists!");
        }

        return  responseData;
    }
    //Note
    @GetMapping(path = "/statistic/{username}")
    public ResponseData getStatistic(@PathVariable("username") String username) {
        ResponseData responseData = new ResponseData();
        List<Object> statistic = userService.getStatistic(username);
        if (statistic != null) {
            responseData.setStatus(200);
            responseData.setMessage("Get user successfully");
            responseData.setDataList(statistic);
        } else {
            responseData.setStatus(500);
            responseData.setMessage("The tested assignment of "+ username + " doesn't exists!");
        }

        return  responseData;
    }
    //Note
    @GetMapping(path = "/statistic/topic-set/{topic_set_code}")
    public ResponseData getTopicSetStatistic(@PathVariable("topic_set_code") String topic_set_code) {
        ResponseData responseData = new ResponseData();
        List<Object> statistic = userService.getTopicSetStatistic(topic_set_code);
        if (statistic != null) {
            responseData.setStatus(200);
            responseData.setMessage("Get user successfully");
            responseData.setDataList(statistic);
        } else {
            responseData.setStatus(500);
            responseData.setMessage("The topic set of "+ topic_set_code + " doesn't exists!");
        }

        return  responseData;
    }

    //Note
//    Trieu
    @GetMapping("{username}/friends")
    public ResponseEntity<List<UserDtoResponse>> getFriends(@PathVariable(name = "username") String username){
        List<UserDtoResponse> responses = userService.getFriends(username);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    //Note
    @GetMapping("{username}/notification")
    public ResponseEntity<List<NotificationResponse>> getNotification(@PathVariable(name = "username") String username){
        List<NotificationResponse> responses = userService.getNotification(username);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    //Note
    @PostMapping(path = "/update")
    @ResponseBody
    public ResponseData updateUser(@RequestParam String username,
                                    @RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam String phoneNumber) {
        boolean isSuccess = userService.updateUser2(username,name,email,phoneNumber);

        ResponseData responseData = new ResponseData();
        if(isSuccess) {
            responseData.setStatus(200);
            responseData.setMessage("Updated user successfully");
        }

        return responseData;
    }

}
