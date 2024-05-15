package com.group3.multiplechoiceAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Topic_Set {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long topicSetID;

    private String topicSetName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private Float duration;

    @ManyToOne
    @JoinColumn(name="topicID")
    private Topic topic;

    @OneToMany(mappedBy = "topicSet", cascade = CascadeType.MERGE)
    private List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "topicSet")
    private List<Share> shareList = new ArrayList<>();

    @OneToMany(mappedBy = "topicSet")
    private List<Assignment> assignmentList = new ArrayList<>();
}
