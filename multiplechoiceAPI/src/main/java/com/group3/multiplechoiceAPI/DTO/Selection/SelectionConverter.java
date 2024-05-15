package com.group3.multiplechoiceAPI.DTO.Selection;

import com.group3.multiplechoiceAPI.DTO.Topic.TopicConverter;
import com.group3.multiplechoiceAPI.Model.Question;
import com.group3.multiplechoiceAPI.Model.Selection;
import com.group3.multiplechoiceAPI.Model.Topic;
import com.group3.multiplechoiceAPI.Service.QuestionService;

public class SelectionConverter {
    private final QuestionService questionService;

    public SelectionConverter(QuestionService questionService) {
        this.questionService = questionService;
    }

    public static SelectionDTO toDTO(Selection selection) {
        if (selection == null) return null;

        SelectionDTO selectionDTO = new SelectionDTO();
        selectionDTO.setSelectionID(selection.getSelectionID());
        selectionDTO.setSelectionContent(selection.getSelectionContent());
        selectionDTO.setQuestionID(selection.getQuestion().getQuestionID());
        return selectionDTO;
    }

    public Selection toEntity(SelectionDTO selectionDTO) {
        if (selectionDTO == null) return null;

        Selection selection = new Selection();
        selection.setSelectionID(selectionDTO.getSelectionID());
        selection.setSelectionContent(selectionDTO.getSelectionContent());

        Question question = questionService.getQuestionByID(selectionDTO.getQuestionID());
        selection.setQuestion(question);
        return selection;
    }
}
