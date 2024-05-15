package com.group3.multiplechoiceAPI.DTO.Selection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SelectionDTO {
    private Long selectionID;
    private String selectionContent;
    private Long questionID;
}
