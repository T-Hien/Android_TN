package com.group3.multiplechoiceAPI.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class DetailedAssignmentKey implements Serializable {
    @Column(name="assignmentID")
    private Long assignmentID;

    @Column(name="questionID")
    private Long questionID;
}
