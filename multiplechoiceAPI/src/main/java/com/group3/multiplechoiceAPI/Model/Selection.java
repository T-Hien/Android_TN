package com.group3.multiplechoiceAPI.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Selection {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long selectionID;
    private String selectionContent;

    @ManyToOne
    @JoinColumn(name="questionID")
    private Question question;
}
