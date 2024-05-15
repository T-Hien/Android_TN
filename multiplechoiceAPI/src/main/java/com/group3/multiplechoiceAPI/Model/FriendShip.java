package com.group3.multiplechoiceAPI.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class FriendShip {

    @EmbeddedId
    private FriendshipId friendshipId;

    @ManyToOne
    @MapsId("username1")
    @JoinColumn()
    private User user1;

    @ManyToOne
    @MapsId("username2")
    @JoinColumn()
    private User user2;

    private int status;
}
