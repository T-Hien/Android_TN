
package com.group3.multiplechoiceAPI.Service;

import com.group3.multiplechoiceAPI.Model.Question;
import com.group3.multiplechoiceAPI.Model.Selection;
import com.group3.multiplechoiceAPI.Repository.SelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectionService {
    private final SelectionRepository selectionRepository;

    @Autowired
    public SelectionService(SelectionRepository selectionRepository) {
        this.selectionRepository = selectionRepository;
    }
    public List<Selection> getAllSelection(){
        return selectionRepository.findAll();
    }

    public List<Selection> getAllQuestionsByQuestionID(Long questionID) {
        return selectionRepository.findAllSelectionsByQuestionID(questionID);
    }
}
