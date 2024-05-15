package com.group3.multiplechoiceAPI.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Share {
    @EmbeddedId
    private ShareKey id;

    @ManyToOne
    @MapsId("username")
    private User user;

    @ManyToOne
    @MapsId("topicSetID")
    private Topic_Set topicSet;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sharedDate;

    private String shareContent;
}
