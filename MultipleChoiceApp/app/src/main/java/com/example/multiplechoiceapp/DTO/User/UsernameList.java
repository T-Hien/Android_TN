package com.example.multiplechoiceapp.DTO.User;


import java.util.List;

public class UsernameList {
    List<Username> users;

    public UsernameList(List<Username> users) {
        this.users = users;
    }

    public List<Username> getUsernames() {
        return users;
    }

    public void setUsernames(List<Username> usernames) {
        this.users = usernames;
    }

    @Override
    public String toString() {
        return "UsernameList{" +
                "usernames=" + users +
                '}';
    }
}
