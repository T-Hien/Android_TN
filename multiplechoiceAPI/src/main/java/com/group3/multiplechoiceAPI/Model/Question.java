package com.group3.multiplechoiceAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Question {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long questionID;
    private String questionContent;
    private String answer;
    private int level;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.MERGE)
    private List<Selection> selectionList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="topicSetID")
    private Topic_Set topicSet;

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private List<Detailed_Assignment> detailedAssignmentList;
}
