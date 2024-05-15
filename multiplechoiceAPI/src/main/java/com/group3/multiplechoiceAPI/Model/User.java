package com.group3.multiplechoiceAPI.Model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class User {
    private @Id String username;
    private String password;

    @Nullable
    private String name;

    private int gender;

    @Nullable
    private Date dayOfBirth;

    @Nullable
    private String email;

    private String phoneNumber;

    public User() {
        this.gender = 3;
    }

    @OneToMany(mappedBy = "user")
    private List<Assignment> assignmentList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Topic> topicList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Share> shareList = new ArrayList<>();

    @OneToMany(mappedBy = "user1",fetch = FetchType.LAZY)
    private List<FriendShip> friendshipsAsUser1 = new ArrayList<>();

    @OneToMany(mappedBy = "user2",fetch = FetchType.LAZY)
    private List<FriendShip> friendshipsAsUser2 = new ArrayList<>();
}
