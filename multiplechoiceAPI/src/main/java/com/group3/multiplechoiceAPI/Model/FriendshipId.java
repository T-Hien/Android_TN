package com.group3.multiplechoiceAPI.Model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class FriendshipId implements Serializable {
    private String username1;
    private String username2;
}
