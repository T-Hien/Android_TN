package com.group3.multiplechoiceAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Detailed_Assignment {
    @EmbeddedId
    private DetailedAssignmentKey id;

    @ManyToOne
    @MapsId("assignmentID")
    @JoinColumn(name="assignmentID")
    private Assignment assignment;

    @ManyToOne
    @MapsId("questionID")
    @JoinColumn(name="questionID")
    private Question question;

    private String selectedAnswer;
}
